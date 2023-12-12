package com.webflux.model.metadata

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = SpringCourseMetadata::class, name = CourseConstants.SPRING_COURSE_TYPE),
    JsonSubTypes.Type(value = EnglishCourseMetadata::class, name = CourseConstants.ENGLISH_COURSE_TYPE)
)
open class CourseMetadata(
    @JsonIgnore
    open val type: String?
) : Serializable