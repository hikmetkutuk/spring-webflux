package com.webflux.dto;

import java.util.List;

public record StudentResponse(String name, String dateOfBirth, String email, List<CourseResponse> courses) {
}
