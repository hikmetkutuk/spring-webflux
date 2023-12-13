package com.webflux.service;

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
}
