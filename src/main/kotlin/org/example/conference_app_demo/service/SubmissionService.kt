package org.example.conference_app_demo.service

import org.example.conference_app_demo.dto.SubmissionDTO
import org.example.conference_app_demo.model.Submission
import org.springframework.stereotype.Service

@Service
class SubmissionService {


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