package com.webflux.service;

import com.webflux.dto.response.CourseResponse;
import com.webflux.dto.response.StudentListResponse;
import com.webflux.dto.request.StudentRequest;
import com.webflux.dto.response.StudentResponse;
import com.webflux.model.Student;
import com.webflux.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseService courseService;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    public Mono<StudentListResponse> getAllStudentWithCourses() {
        return studentRepository.findAll()
                .flatMap(
                        student -> {
                            List<Mono<CourseResponse>> courseList = student.getCourses()
                                    .stream()
                                    .map(courseId -> courseService.getCourseById(UUID.fromString(courseId)))
                                    .collect(Collectors.toList());

                            return Flux.combineLatest(courseList, objects -> {
                                List<CourseResponse> courses = Arrays.stream(objects)
                                        .map(o -> (CourseResponse) o)
                                        .collect(Collectors.toList());

                                return new StudentResponse(student.getName(), student.getDateOfBirth().toString(), student.getEmail(), courses);
                            });
                        })
                .collectList()
                .map(StudentListResponse::new);
    }

    public Mono<StudentResponse> createStudent(StudentRequest request) {
        Set<String> courses = new HashSet<>(request.courses());

        return Mono.fromCallable(() -> LocalDate.parse(request.dateOfBirth()))
                .flatMap(dateOfBirth -> {
                    Student newStudent = Student.builder()
                            .id(UUID.randomUUID())
                            .name(request.name())
                            .dateOfBirth(dateOfBirth)
                            .email(request.email())
                            .courses(courses)
                            .build();

                    return studentRepository.save(newStudent)
                            .flatMap(student -> {
                                List<Mono<CourseResponse>> courseResponses = student.getCourses()
                                        .stream()
                                        .map(courseId -> courseService.getCourseById(UUID.fromString(courseId)))
                                        .collect(Collectors.toList());

                                return Flux.merge(courseResponses)
                                        .collectList()
                                        .map(courseDetails -> new StudentResponse(student.getName(), dateOfBirth.toString(), student.getEmail(), courseDetails));
                            });
                })
                .doOnSuccess(student -> logger.info("Student created successfully: {}", student.email()))
                .onErrorResume(DateTimeParseException.class, Mono::error)
                .doOnError(DataIntegrityViolationException.class, e -> logger.error("Email must be unique!: {}", e.getMessage()))
                .onErrorResume(DataIntegrityViolationException.class, e -> Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email must be unique!")))
                .onErrorResume(Mono::error);
    }
}
