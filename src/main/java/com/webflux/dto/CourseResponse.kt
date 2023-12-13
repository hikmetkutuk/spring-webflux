package com.webflux.dto

import com.webflux.model.metadata.CourseMetadata

data class CourseResponse(
    val name: String,
    val description: String,
    val duration: Int,
    val courseMetadata: CourseMetadata
)