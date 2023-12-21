package com.webflux.router;

import com.webflux.dto.CourseResponse;
import com.webflux.handler.CourseHandler;
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
public class CourseRouter {
    public static final String COURSE_ROUTE = "/api/v1/course";
    public static final String COURSE_CREATE = "/create";
    public static final String COURSE_LIST = "/list";

    private final CourseHandler courseHandler;

    public CourseRouter(CourseHandler courseHandler) {
        this.courseHandler = courseHandler;
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = COURSE_CREATE,
                    method = RequestMethod.POST,
                    operation =
                    @Operation(
                            operationId = "createCourse",
                            summary = "Create new course",
                            description = "Create new course",
                            responses =
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Create new course",
                                    content = @Content(schema = @Schema(implementation = CourseResponse.class))
                            )
                    )
            ),
            @RouterOperation(
                    path = COURSE_LIST,
                    method = RequestMethod.GET,
                    operation =
                    @Operation(
                            operationId = "getAllCourses",
                            summary = "get All Courses",
                            description = "get All Courses",
                            responses =
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "get All Courses",
                                    content = @Content(schema = @Schema(implementation = CourseResponse.class))
                            )
                    )
            ),
    })

    public RouterFunction<ServerResponse> createCourse() {
        return RouterFunctions.nest(
                path(COURSE_ROUTE),
                RouterFunctions.route(
                        POST(COURSE_CREATE).and(accept(MediaType.APPLICATION_JSON)),
                        courseHandler::handleCreateCourse)
        );
    }

    public RouterFunction<ServerResponse> getAllCourses() {
        return RouterFunctions.nest(
                path(COURSE_ROUTE),
                RouterFunctions.route(
                        GET(COURSE_LIST).and(accept(MediaType.APPLICATION_JSON)),
                        courseHandler::handleGetAllCourses)
        );
    }
}
