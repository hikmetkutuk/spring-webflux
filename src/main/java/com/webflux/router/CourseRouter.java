package com.webflux.router;

import com.webflux.handler.CourseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CourseRouter {
    public static final String COURSE_ROUTE = "/api/v1/course";
    public static final String COURSE_CREATE = COURSE_ROUTE + "/create";
    public static final String COURSE_LIST = COURSE_ROUTE + "/list";

    private final CourseHandler courseHandler;

    public CourseRouter(CourseHandler courseHandler) {
        this.courseHandler = courseHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> courseRoutes() {
        return route()
                .GET(COURSE_LIST, accept(APPLICATION_JSON), courseHandler::handleGetAllCourses)
                .POST(COURSE_CREATE, accept(APPLICATION_JSON), courseHandler::handleCreateCourse)
                .build();
    }
}
