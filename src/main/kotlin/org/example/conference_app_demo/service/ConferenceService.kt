package org.example.conference_app_demo.service

import org.example.conference_app_demo.model.Conference
import org.example.conference_app_demo.repository.ConferenceRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class ConferenceService(private val conferenceRepository: ConferenceRepository,
    private val scheduleService: ScheduleService) {

    fun save(conference: Conference): Conference {
        if (conference.startDate.isAfter(conference.endDate)) {
            throw IllegalArgumentException("Start date must be before end date")
        }

        if (conferenceRepository.existsByName(conference.name)) {
            throw Exception("Conference with name ${conference.name} already exists")
        }

        return conferenceRepository.save(conference)
    }

    fun update(updatedConference: Conference, id:Long): Conference {
        val existingConference = conferenceRepository.findById(id)
            .orElseThrow { Exception("Conference with id $id not found") }

        existingConference.name = updatedConference.name
        existingConference.city = updatedConference.city
        existingConference.country = updatedConference.country
        existingConference.description = updatedConference.description
        existingConference.startDate = updatedConference.startDate
        existingConference.endDate = updatedConference.endDate
        existingConference.category = updatedConference.category

        // Manage schedules
        val existingSchedules = existingConference.schedules
        val updatedSchedules = updatedConference.schedules

        // Remove schedules that are no longer present in the update
        val schedulesToRemove = existingSchedules.filter { existing ->
            updatedSchedules.none { updated -> updated.id == existing.id }
        }
        existingSchedules.removeAll(schedulesToRemove)

        // Update existing schedules or add new ones
        for (updatedSchedule in updatedSchedules) {
            val existingSchedule = existingSchedules.find { it.id == updatedSchedule.id }
            if (existingSchedule != null) {
                // For existing schedules, update the fields
                existingSchedule.startDate = updatedSchedule.startDate
            } else {
                // For new schedules, add them to the list
                existingSchedules.add(updatedSchedule)
            }
        }

        existingConference.updatedAt = LocalDateTime.now()

        return conferenceRepository.save(existingConference)
    }

    fun findById(id: Long): Conference {
        return conferenceRepository.findById(id).orElseThrow { Exception("Conference with id $id not found") }
    }

    fun findAll(): List<Conference> {
        return conferenceRepository.findAll()
    }

    fun deleteById(id: Long){
        if(!conferenceRepository.existsById(id))
            throw Exception("Conference with id $id not found")

        conferenceRepository.deleteById(id);
    }

    fun getNameById(id: Long): String {
        return conferenceRepository.findById(id)
            .map { it.name } // Map the result to the conference name
            .orElseThrow { Exception("Conference with id $id not found") }
    }

    fun delete(conference: Conference) = conferenceRepository.delete(conference);
    fun count() = conferenceRepository.count();
    fun existsById(id: Long) = conferenceRepository.existsById(id);

    /*fun toDTO(conference: Conference): ConferenceDTO {
        return ConferenceDTO(
            id = conference.id,
            name = conference.name,
            city = conference.city,
            url = conference.url,
            description = conference.description,
            startDate = conference.startDate,
            endDate = conference.endDate,
            tags = conference.tags,
            schedules = conference.schedules.map { scheduleService.toDTO(it) }, // Use scheduleService to map schedules
            createdAt = conference.createdAt,
            updatedAt = conference.updatedAt
        )
    }

    fun toEntity(dto: ConferenceDTO): Conference {
        val conference = Conference(
            id = dto.id,
            name = dto.name,
            city = dto.city,
            url = dto.url,
            description = dto.description,
            startDate = dto.startDate,
            endDate = dto.endDate,
            tags = dto.tags,
            schedules = mutableListOf() // Initialize empty for now
        )

        // Map schedules and pass the current Conference instance
        conference.schedules = dto.schedules.map { scheduleService.toEntity(it, conference) }.toMutableList()

        return conference
    }*/


}