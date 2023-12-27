package com.webflux.dto.response;

import com.webflux.model.metadata.CourseMetadata;

public record CourseResponse(String name, String description, Integer duration, CourseMetadata courseMetadata) {
}
