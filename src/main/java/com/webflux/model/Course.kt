package com.webflux.model

import com.webflux.model.metadata.CourseMetadata
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.io.Serializable
import java.util.*

@Table(name = "courses")
data class Course(
    @Id
    val courseId: UUID? = null,

    val name: String,

    val description: String,

    val duration: Int,

    val courseMetadata: CourseMetadata,

    @Transient
    val isUpdated: Boolean = false
) : Serializable, Persistable<UUID> {
    override fun getId(): UUID? {
        return courseId
    }

    override fun isNew(): Boolean {
        return !isUpdated
    }
}