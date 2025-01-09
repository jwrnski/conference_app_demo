package org.example.conference_app_demo.dto

import java.io.Serializable
import java.time.LocalDateTime

/**
 * DTO for {@link org.example.conference_app_demo.model.Schedule}
 */
data class ScheduleDTO(
    val id: Long = 0,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val conferenceId: Long,
    val presentations: List<PresentationDTO> = mutableListOf(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
): Serializable