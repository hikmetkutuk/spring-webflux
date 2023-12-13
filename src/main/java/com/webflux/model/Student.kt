package com.webflux.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.io.Serializable
import java.time.LocalDate
import java.util.*

@Table("student")
data class Student(
    @Id
    val studentId: UUID? = null,

    val name: String,

    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val dateOfBirth: LocalDate,

    val email: String,

    @Column("course_id")
    val courses: Set<String>,

    @Transient
    val isUpdated: Boolean = false
) : Serializable, Persistable<UUID> {
    override fun getId(): UUID? {
        return studentId
    }

    override fun isNew(): Boolean {
        return !isUpdated
    }
}