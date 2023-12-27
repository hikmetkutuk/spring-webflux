package com.webflux.router;

import com.webflux.handler.StudentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class StudentRouter {
    public static final String STUDENT_ROUTE = "/api/v1/student";
    public static final String STUDENT_CREATE_ROUTE = STUDENT_ROUTE + "/create";
    public static final String STUDENT_COURSES_ROUTE = STUDENT_ROUTE + "/courses";

    private final StudentHandler studentHandler;

    public StudentRouter(StudentHandler studentHandler) {
        this.studentHandler = studentHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> studentRoutes() {
        return route()
                .GET(STUDENT_COURSES_ROUTE, accept(APPLICATION_JSON), studentHandler::handleGetAllStudentWithCourses)
                .POST(STUDENT_CREATE_ROUTE, accept(APPLICATION_JSON), studentHandler::handleCreateStudent)
                .build();
    }
}
