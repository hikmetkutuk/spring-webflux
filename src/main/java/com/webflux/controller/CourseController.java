package com.webflux.controller;

import com.webflux.dto.CourseRequest;
import com.webflux.dto.CourseResponse;
import com.webflux.model.Course;
import com.webflux.repository.CourseRepository;
import com.webflux.service.CourseService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/api/course")
public class CourseController {
    private final CourseService courseService;
    private final CourseRepository courseRepository;

    public CourseController(CourseService courseService,
                            CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    @PostMapping("/create")
    public Mono<CourseResponse> createCourse(@RequestBody CourseRequest request) {
        return courseService.createCourse(request);
    }

    @GetMapping("/list")
    public Flux<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
}
