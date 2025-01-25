package org.example.conference_app_demo.service

import org.springframework.stereotype.Service
import org.example.conference_app_demo.model.Presentation
import org.example.conference_app_demo.repository.PresentationRepository
import java.time.LocalDateTime

@Service
class PresentationService(private val userService: UserService, private val presentationRepository: PresentationRepository) {

    fun save(presentation: Presentation): Presentation {
        return presentationRepository.save(presentation)
    }

    fun findById(id: Long): Presentation {
        return presentationRepository.findById(id).orElseThrow{Exception("Presentation not found")}
    }

    fun findAll(): List<Presentation> {
        return presentationRepository.findAll()
    }

    fun deleteById(id: Long) {
        return presentationRepository.deleteById(id)
    }

    fun update(id: Long, presentation: Presentation): Presentation {
        val existingPresentation = findById(presentation.id)
        existingPresentation.title = presentation.title
        existingPresentation.description = presentation.description
        existingPresentation.startTime = presentation.startTime
        existingPresentation.endTime = presentation.endTime
        existingPresentation.updatedAt = LocalDateTime.now()
        existingPresentation.schedule = presentation.schedule
        existingPresentation.speakers = presentation.speakers
        return presentationRepository.save(existingPresentation)
    }



    /*fun toDTO(presentation: Presentation): PresentationDTO {
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
    }*/
}