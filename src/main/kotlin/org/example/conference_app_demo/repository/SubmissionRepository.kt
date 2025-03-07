package org.example.conference_app_demo.repository

import org.example.conference_app_demo.model.Submission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SubmissionRepository : JpaRepository<Submission, Long> {

    @Query("SELECT s FROM Submission s WHERE s.conference.id IN :conferenceIds")
    fun findByConferenceIds(@Param("conferenceIds") conferenceIds: List<Long>): List<Submission>
}