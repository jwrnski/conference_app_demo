package org.example.conference_app_demo.dto

import java.io.Serializable
import java.time.LocalDateTime

/**
 * DTO for {@link org.example.conference_app_demo.model.Conference}
 */
data class ConferenceDTO(
    val id: Long = 0,
    val name: String,
    val city: String,
    val url: String,
    val description: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val tags: String,
    val schedules: List<ScheduleDTO> = mutableListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) : Serializable