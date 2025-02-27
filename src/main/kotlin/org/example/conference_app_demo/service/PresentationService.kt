package org.example.conference_app_demo.service

import org.example.conference_app_demo.dto.PresentationDto
import org.springframework.stereotype.Service
import org.example.conference_app_demo.model.Presentation
import org.example.conference_app_demo.model.User
import org.example.conference_app_demo.repository.PresentationRepository
import java.time.LocalDateTime

@Service
class PresentationService(
    private val userService: UserService,
    private val presentationRepository: PresentationRepository,
    private val scheduleService: ScheduleService
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

    fun toEntity(presentationDto: PresentationDto): Presentation {
        return Presentation(
            title = presentationDto.title!!,
            description = presentationDto.description!!,
            startTime = presentationDto.startTime!!,
            endTime = presentationDto.endTime!!,
            schedule = scheduleService.findById(presentationDto.scheduleId!!),
            speakers = addToSpeakers(presentationDto.speakerId!!, presentationDto.id!!)
        )
    }

    fun addToSpeakers(speakerId: Long, presentationId: Long): MutableList<User> {
        val presentation = findById(presentationId)
        val user = userService.findById(speakerId)
        presentation.speakers.add(user)
        return presentationRepository.save(presentation).speakers
    }
}