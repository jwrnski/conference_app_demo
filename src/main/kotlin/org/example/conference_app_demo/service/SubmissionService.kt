package org.example.conference_app_demo.service

import org.example.conference_app_demo.dto.SubmissionDTO
import org.example.conference_app_demo.model.Submission
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

    fun update(id: Long, submission: Submission): Submission {
        val existingSubmission = findById(id) ?: throw Exception("Submission with id $id not found")
        existingSubmission.abstractTitle = submission.abstractTitle
        existingSubmission.content = submission.content
        existingSubmission.author = submission.author
        existingSubmission.conference = submission.conference
        existingSubmission.topic = submission.topic
        existingSubmission.status = submission.status
        existingSubmission.rating = submission.rating
        existingSubmission.comments = submission.comments
        existingSubmission.updatedAt = LocalDateTime.now()
        return submissionRepository.save(existingSubmission)
    }

    fun toDTO(submission: Submission): SubmissionDTO {
        return SubmissionDTO(
            id = submission.id,
            abstractTitle = submission.abstractTitle,
            content = submission.content,
            author = submission.author,
            conference = submission.conference,
            topic = submission.topic,
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
            abstractTitle = dto.abstractTitle,
            content = dto.content,
            author = dto.author,
            conference = dto.conference,
            topic = dto.topic,
            status = dto.status,
            rating = dto.rating,
            comments = dto.comments,
            createdAt = dto.createdAt,
            updatedAt = dto.updatedAt
        )
    }
}