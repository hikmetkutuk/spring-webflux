package com.webflux.router;

import com.webflux.dto.StudentListResponse;
import com.webflux.dto.StudentResponse;
import com.webflux.handler.StudentHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class StudentRouter {
    public static final String STUDENT_ROUTE = "/api/v1/student";
    public static final String STUDENT_CREATE_ROUTE = "/create";
    public static final String STUDENT_COURSES_ROUTE = "/courses";

    private final StudentHandler studentHandler;

    public StudentRouter(StudentHandler studentHandler) {
        this.studentHandler = studentHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = STUDENT_COURSES_ROUTE,
                    method = RequestMethod.GET,
                    operation =
                    @Operation(
                            operationId = "getAllStudentWithCourses",
                            summary = "Get all students with courses",
                            description = "Get all students with courses",
                            responses =
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Get students with courses",
                                    content = @Content(schema = @Schema(implementation = StudentListResponse.class))
                            )
                    )
            ),
            @RouterOperation(
                    path = STUDENT_CREATE_ROUTE,
                    method = RequestMethod.POST,
                    operation =
                    @Operation(
                            operationId = "createStudent",
                            summary = "Create new student",
                            description = "Create new student",
                            responses =
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Create new student",
                                    content = @Content(schema = @Schema(implementation = StudentResponse.class))
                            )
                    )
            )
    })

    public RouterFunction<ServerResponse> createStudent() {
        return RouterFunctions.nest(
                path(STUDENT_ROUTE),
                RouterFunctions.route(
                        POST(STUDENT_CREATE_ROUTE).and(accept(MediaType.APPLICATION_JSON)),
                        studentHandler::handleCreateStudent)
        );
    }

    public RouterFunction<ServerResponse> getAllStudentWithCourses() {
        return RouterFunctions.nest(
                path(STUDENT_ROUTE),
                RouterFunctions.route(
                        GET(STUDENT_COURSES_ROUTE).and(accept(MediaType.APPLICATION_JSON)),
                        studentHandler::handleGetAllStudentWithCourses)
        );
    }
}
