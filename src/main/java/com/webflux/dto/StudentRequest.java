package com.webflux.dto;

import java.time.LocalDate;
import java.util.List;

public record StudentRequest(String name, LocalDate dateOfBirth, String email, List<CourseResponse> courses) {
}
