package com.webflux.model.metadata

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName(CourseConstants.ENGLISH_COURSE_TYPE)
data class EnglishCourseMetadata(
    val level: String,
    val books: List<String>,
    override val type: String?
) : CourseMetadata(type)