package org.example.conference_app_demo.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.io.Serializable
import java.time.LocalDateTime

/**
 * DTO for {@link org.example.conference_app_demo.model.Institution}
 */
data class InstitutionDTO(
    val id: Long = 0,
    val name: String,
    val country: String,
    val city: String,
    val logo: String,
    val url: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) : Serializable