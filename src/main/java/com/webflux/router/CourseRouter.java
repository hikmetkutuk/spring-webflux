package com.webflux.router;

import com.webflux.handler.CourseHandler;
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
public class CourseRouter {
    public static final String COURSE_ROUTE = "/api/v1/course";
    public static final String COURSE_CREATE = COURSE_ROUTE + "/create";
    public static final String COURSE_LIST = COURSE_ROUTE + "/list";

    private final CourseHandler courseHandler;

    public CourseRouter(CourseHandler courseHandler) {
        this.courseHandler = courseHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = COURSE_LIST,
                    method = RequestMethod.GET,
                    operation = @Operation(
                            tags = {"Course"},
                            operationId = "getAllCourses",
                            summary = "Get all courses",
                            description = "Get a list of all courses",
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
                    path = COURSE_CREATE,
                    method = RequestMethod.POST,
                    operation = @Operation(
                            tags = {"Course"},
                            operationId = "createCourse",
                            summary = "Create a new course",
                            description = "Create a new course with the provided data",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Course created successfully"
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
    public RouterFunction<ServerResponse> courseRoutes() {
        return route()
                .GET(COURSE_LIST, accept(APPLICATION_JSON), courseHandler::handleGetAllCourses)
                .POST(COURSE_CREATE, accept(APPLICATION_JSON), courseHandler::handleCreateCourse)
                .build();
    }
}
