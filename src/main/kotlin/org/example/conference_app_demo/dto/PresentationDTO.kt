package org.example.conference_app_demo.dto

import java.io.Serializable
import java.time.LocalDateTime

/**
 * DTO for {@link org.example.conference_app_demo.model.Presentation}
 */
data class PresentationDTO(
    val id: Long = 0,
    val title: String,
    val description: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val scheduleId: Long, // Reference to the Schedule
    val userIds: List<Long> = listOf(), // IDs of associated Users
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) : Serializable