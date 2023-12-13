package com.webflux.service;

import com.webflux.dto.CourseResponse;
import com.webflux.dto.StudentListResponse;
import com.webflux.dto.StudentResponse;
import com.webflux.repository.StudentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseService courseService;

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

                                return new StudentResponse(student.getName(), student.getEmail(), courses);
                            });
                        })
                .collectList()
                .map(StudentListResponse::new);
    }
}
