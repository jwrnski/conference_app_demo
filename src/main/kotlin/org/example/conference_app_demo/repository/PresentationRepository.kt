package org.example.conference_app_demo.repository

import org.example.conference_app_demo.model.Presentation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PresentationRepository : JpaRepository<Presentation, Long> {

    @Query("SELECT p FROM Presentation p WHERE p.conference.id = :conferenceId")
    fun findByConferenceId(@Param("conferenceId") conferenceId: Long): List<Presentation>

    fun getAllByConferenceId(conferenceId: Long): List<Presentation>

}