package org.example.conference_app_demo.repository

import org.example.conference_app_demo.model.Submission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubmissionRepository : JpaRepository<Submission, Long> {
}