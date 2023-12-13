package com.webflux.dto

data class StudentResponse(
    val name: String,
    val email: String,
    val courses: List<CourseResponse>
)