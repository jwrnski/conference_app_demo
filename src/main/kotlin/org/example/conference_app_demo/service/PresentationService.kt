package org.example.conference_app_demo.service

import org.springframework.stereotype.Service
import org.example.conference_app_demo.dto.PresentationDTO
import org.example.conference_app_demo.model.Presentation
import org.example.conference_app_demo.dto.ScheduleDTO
import org.example.conference_app_demo.model.Schedule

@Service
class PresentationService(private val userService: UserService) {

    fun toDTO(presentation: Presentation): PresentationDTO {
        return PresentationDTO(
            id = presentation.id,
            title = presentation.title,
            description = presentation.description,
            startTime = presentation.startTime,
            endTime = presentation.endTime,
            scheduleId = presentation.schedule.id,
            userIds = presentation.users.map { it.id }, // Extract user IDs
            createdAt = presentation.createdAt,
            updatedAt = presentation.updatedAt
        )
    }

    fun toEntity(dto: PresentationDTO, schedule: Schedule): Presentation {
        return Presentation(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            startTime = dto.startTime,
            endTime = dto.endTime,
            schedule = schedule, // Pass the parent Schedule entity
            users = userService.findByIds(dto.userIds) // Use userService to resolve User entities
        )
    }
}