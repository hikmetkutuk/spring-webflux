package com.webflux.handler;

import com.webflux.dto.request.CourseRequest;
import com.webflux.dto.response.CourseResponse;
import com.webflux.service.CourseService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CourseHandler {
    private final CourseService courseService;

    public CourseHandler(CourseService courseService) {
        this.courseService = courseService;
    }

    public Mono<ServerResponse> handleCreateCourse(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(CourseRequest.class)
                .flatMap(courseService::createCourse)
                .flatMap(createdCourse -> ServerResponse.ok().bodyValue(createdCourse))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }

    public Mono<ServerResponse> handleGetAllCourses(ServerRequest request) {
        Flux<CourseResponse> courses = courseService.getAllCourses();
        return ServerResponse.ok().body(courses, CourseResponse.class);
    }
}
