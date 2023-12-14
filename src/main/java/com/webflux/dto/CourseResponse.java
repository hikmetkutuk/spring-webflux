package com.webflux.dto;

import com.webflux.model.metadata.CourseMetadata;

public record CourseResponse(String name, String description, Integer duration, CourseMetadata courseMetadata) {
}
