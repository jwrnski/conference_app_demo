package org.example.conference_app_demo.service

import org.example.conference_app_demo.model.Schedule
import org.example.conference_app_demo.repository.ScheduleRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ScheduleService(  private val presentationService: PresentationService,
                        private val scheduleRepository: ScheduleRepository) {


    fun save(schedule: Schedule): Schedule {
        return scheduleRepository.save(schedule)
    }

    fun findById(id: Long): Schedule {
        return scheduleRepository.findById(id).orElseThrow{Exception("Schedule not found")}
    }

    fun findByConferenceId(id: Long): MutableList<Schedule> {
        return scheduleRepository.findScheduleByConferenceId(id)
    }

    fun findAll(): List<Schedule> {
        return scheduleRepository.findAll()
    }

    fun deleteById(id: Long) {
        return scheduleRepository.deleteById(id)
    }

    fun update(id: Long, schedule: Schedule): Schedule {
        val existingSchedule = findById(schedule.id)
        existingSchedule.startDate = schedule.startDate
        existingSchedule.updatedAt = LocalDateTime.now()
        existingSchedule.presentations = schedule.presentations
        existingSchedule.conference = schedule.conference
        return scheduleRepository.save(existingSchedule)
    }


    /*fun toDTO(schedule: Schedule): ScheduleDTO {
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
    }*/
}