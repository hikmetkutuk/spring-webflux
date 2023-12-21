package com.webflux.controller;

import com.webflux.dto.CourseRequest;
import com.webflux.dto.CourseResponse;
import com.webflux.model.Course;
import com.webflux.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/course")
@Tag(name = "course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create")
    public Mono<CourseResponse> createCourse(@RequestBody CourseRequest request) {
        return courseService.createCourse(request);
    }

    @GetMapping("/list")
    public Flux<CourseResponse> getAllCourses() {
        return courseService.getAllCourses();
    }
}
