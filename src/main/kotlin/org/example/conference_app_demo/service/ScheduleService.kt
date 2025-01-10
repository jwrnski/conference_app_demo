package org.example.conference_app_demo.service

import org.example.conference_app_demo.dto.ScheduleDTO
import org.example.conference_app_demo.model.Conference
import org.example.conference_app_demo.model.Schedule
import org.springframework.stereotype.Service

@Service
class ScheduleService(private val presentationService: PresentationService) {

    fun toDTO(schedule: Schedule): ScheduleDTO {
        return ScheduleDTO(
            id = schedule.id,
            conferenceId = schedule.conference.id,
            startDate = schedule.startDate,
            endDate = schedule.endDate,
            presentations = schedule.presentations.map { presentationService.toDTO(it) }, // Use presentationService to map presentations
            createdAt = schedule.createdAt,
            updatedAt = schedule.updatedAt
        )
    }

    fun toEntity(dto: ScheduleDTO, conference: Conference): Schedule {
        val schedule = Schedule(
            id = dto.id,
            conference = conference, // Pass the parent Conference entity
            startDate = dto.startDate,
            endDate = dto.endDate,
            presentations = mutableListOf() // Initialize presentations as an empty list for now
        )

        // Map presentations and assign the current Schedule instance
        schedule.presentations = dto.presentations
            .map { presentationService.toEntity(it, schedule) }
            .toMutableList() // Convert the result to a MutableList


        return schedule
    }
}