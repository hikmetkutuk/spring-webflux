package com.webflux.router;

import com.webflux.handler.StudentHandler;
import com.webflux.model.Course;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RouterOperations({
            @RouterOperation(
                    path = STUDENT_COURSES_ROUTE,
                    method = RequestMethod.GET,
                    operation = @Operation(
                            tags = {"Student"},
                            operationId = "getAllStudentWithCourses",
                            summary = "Get all students with courses",
                            description = "Get all students with courses",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successful operation",
                                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Course.class)))
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = STUDENT_CREATE_ROUTE,
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"Student"},
                            operationId = "createStudent",
                            summary = "Create a new student",
                            description = "Create a new student with the provided data",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Student created successfully"
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Bad request"
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = "Internal server error"
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> studentRoutes() {
        return route()
                .GET(STUDENT_COURSES_ROUTE, accept(APPLICATION_JSON), studentHandler::handleGetAllStudentWithCourses)
                .POST(STUDENT_CREATE_ROUTE, accept(APPLICATION_JSON), studentHandler::handleCreateStudent)
                .build();
    }
}
