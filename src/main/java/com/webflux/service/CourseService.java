package com.webflux.service;

import com.webflux.dto.CourseRequest;
import com.webflux.dto.CourseResponse;
import com.webflux.model.Course;
import com.webflux.repository.CourseRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Flux<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Mono<CourseResponse> getCourseById(UUID id) {
        return courseRepository.findById(id)
                .map(course -> new CourseResponse(course.getName(), course.getDescription(), course.getDuration(), course.getCourseMetadata()));
    }

    public Mono<CourseResponse> createCourse(CourseRequest request) {
        Course newCourse = Course.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .description(request.description())
                .duration(request.duration())
                .courseMetadata(request.courseMetadata())
                .build();

        return courseRepository.save(newCourse)
                .map(course -> new CourseResponse(course.getName(), course.getDescription(), course.getDuration(), course.getCourseMetadata()));
    }
}
