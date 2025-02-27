package org.example.conference_app_demo.service

import org.example.conference_app_demo.dto.PresentationDto
import org.example.conference_app_demo.model.Conference
import org.springframework.stereotype.Service
import org.example.conference_app_demo.model.Presentation
import org.example.conference_app_demo.model.Schedule
import org.example.conference_app_demo.model.User
import org.example.conference_app_demo.repository.PresentationRepository
import java.time.LocalDateTime

@Service
class PresentationService(
    private val userService: UserService,
    private val presentationRepository: PresentationRepository,
) {

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

    fun toEntity(presentationDto: PresentationDto, schedule: Schedule, conference: Conference): Presentation {
        return Presentation(
            conference = conference,
            title = presentationDto.title!!,
            description = presentationDto.description!!,
            startTime = presentationDto.startTime!!,
            endTime = presentationDto.endTime!!,
            schedule = schedule,
            speakers = mutableListOf()
        )
    }

}