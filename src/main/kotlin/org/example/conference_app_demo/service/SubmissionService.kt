package org.example.conference_app_demo.service

import org.example.conference_app_demo.model.Submission
import org.example.conference_app_demo.model.SubmissionStatus
import org.example.conference_app_demo.repository.SubmissionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SubmissionService(private val submissionRepository: SubmissionRepository) {


    fun save(submission: Submission): Submission {
        return submissionRepository.save(submission)
    }

    fun findAll(): List<Submission> {
        return submissionRepository.findAll()
    }

    fun findById(id: Long): Submission? {
        return submissionRepository.findById(id).orElseThrow { Exception("Submission with id $id not found") }
    }

    fun deleteById(id: Long) {
        if(submissionRepository.findById(id).isEmpty) throw Exception()
        submissionRepository.deleteById(id)
    }

    fun updateStatus(id: Long, status: SubmissionStatus){
        val submission = submissionRepository.findById(id)
            .orElseThrow { RuntimeException("Submission not found with id $id") }

        submission.status = status
        if(status == SubmissionStatus.APPROVED)
            submission.comments = "This submission has been approved"
        else if(status == SubmissionStatus.REJECTED)
            submission.comments = "This submission has been rejected"
        submissionRepository.save(submission)

    }

    fun getApprovedSubmissions(): List<Submission> {
        val submissions = submissionRepository.findAll()
        val approvedSubmissions = submissions.filter { it.status == SubmissionStatus.APPROVED }
        return approvedSubmissions
    }

    fun update(id: Long, submission: Submission): Submission {
        val existingSubmission = findById(id) ?: throw Exception("Submission with id $id not found")
        existingSubmission.paperTitle = submission.paperTitle
        existingSubmission.abstract = submission.abstract
        existingSubmission.authors = submission.authors
        existingSubmission.conference = submission.conference
        existingSubmission.topics = submission.topics
        existingSubmission.status = submission.status
        existingSubmission.comments = submission.comments
        existingSubmission.updatedAt = LocalDateTime.now()
        return submissionRepository.save(existingSubmission)
    }

    /*fun toDTO(submission: Submission): SubmissionDTO {
        return SubmissionDTO(
            id = submission.id,
            abstractTitle = submission.paperTitle,
            content = submission.content,
            author = submission.author,
            conference = submission.conference,
            topic = submission.category,
            status = submission.status,
            rating = submission.rating,
            comments = submission.comments,
            createdAt = submission.createdAt,
            updatedAt = submission.updatedAt
        )
    }
    fun toEntity(dto: SubmissionDTO): Submission {
        return Submission(
            id = dto.id,
            paperTitle = dto.abstractTitle,
            content = dto.content,
            author = dto.author,
            conference = dto.conference,
            category = dto.topic,
            status = dto.status,
            rating = dto.rating,
            comments = dto.comments,
            createdAt = dto.createdAt,
            updatedAt = dto.updatedAt
        )
    }*/
}