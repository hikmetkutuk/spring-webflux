package com.webflux.dto;

import java.util.List;

public record StudentListResponse(List<StudentResponse> students) {
}
