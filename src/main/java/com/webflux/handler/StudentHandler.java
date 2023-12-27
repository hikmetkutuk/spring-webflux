package com.webflux.handler;

import com.webflux.dto.request.StudentRequest;
import com.webflux.service.StudentService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class StudentHandler {
    private final StudentService studentService;

    public StudentHandler(StudentService studentService) {
        this.studentService = studentService;
    }

    public Mono<ServerResponse> handleGetAllStudentWithCourses(ServerRequest request) {
        return studentService.getAllStudentWithCourses()
                .flatMap(s -> ServerResponse.ok().bodyValue(s))
                .switchIfEmpty(
                        Mono.defer(() -> Mono.error(new RuntimeException("No student found"))));
    }

    public Mono<ServerResponse> handleCreateStudent(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(StudentRequest.class)
                .flatMap(studentService::createStudent)
                .flatMap(createdStudent -> ServerResponse.ok().bodyValue(createdStudent))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }
}
