package com.webflux.controller;

import com.webflux.dto.StudentRequest;
import com.webflux.dto.StudentResponse;
import com.webflux.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/student")
@Tag(name = "student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public Mono<StudentResponse> createStudent(@RequestBody StudentRequest request) {
        return studentService.createStudent(request);
    }
}
