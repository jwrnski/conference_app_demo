package org.example.conference_app_demo.repository

import org.example.conference_app_demo.model.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ScheduleRepository : JpaRepository<Schedule, Long> {

    fun findScheduleByConferenceId(conferenceId: Long): MutableList<Schedule>
}