package com.webflux.dto.response;

import java.util.List;

public record StudentListResponse(List<StudentResponse> students) {
}
