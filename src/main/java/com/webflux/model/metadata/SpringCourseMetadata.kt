package com.webflux.model.metadata

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName(CourseConstants.SPRING_COURSE_TYPE)
data class SpringCourseMetadata(
    val prerequisites: List<String>,
    val language: String,
    val github: String,
    override val type: String?
) : CourseMetadata(type)