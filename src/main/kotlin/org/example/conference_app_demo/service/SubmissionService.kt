package org.example.conference_app_demo.service

import org.example.conference_app_demo.dto.SubmissionDto
import org.example.conference_app_demo.model.*
import org.example.conference_app_demo.repository.SubmissionRepository
import org.example.conference_app_demo.repository.TopicRepository
import org.example.conference_app_demo.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SubmissionService(
    private val submissionRepository: SubmissionRepository,
    private val userRepository: UserRepository,
    private val conferenceService: ConferenceService,
    private val topicRepository: TopicRepository
) {


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

    fun updateStatus(id: Long, status: SubmissionStatus, comments: String?){
        val submission = submissionRepository.findById(id)
            .orElseThrow { RuntimeException("Submission not found with id $id") }

        submission.status = status

        // Use provided comments if not null or empty, otherwise use default
        submission.comments = if (!comments.isNullOrBlank()) {
            comments
        } else if(status == SubmissionStatus.APPROVED) {
            "This submission has been approved"
        } else if(status == SubmissionStatus.REJECTED) {
            "This submission has been rejected"
        } else {
            submission.comments // Keep existing comments for other statuses
        }

        submissionRepository.save(submission)
    }

    fun getSubmissionsByConferences(conferences: List<Conference>): List<Submission> {
        val conferenceIds = conferences.map { it.id }
        return submissionRepository.findByConferenceIds(conferenceIds)
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

    fun toDto(submission: Submission): SubmissionDto {
        return SubmissionDto(
            id = submission.id,
            paperTitle = submission.paperTitle,
            abstract = submission.abstract,
            filePath = submission.filePath,
            authorId = submission.authors.firstOrNull()?.id,
            conferenceId = submission.conference?.id,
            topicId = submission.topics.firstOrNull()?.id,
        )
    }

    fun toEntity(submissionDto: SubmissionDto): Submission {
        return Submission(
            paperTitle = submissionDto.paperTitle,
            abstract = submissionDto.abstract,
            filePath = submissionDto.filePath,
            authors = findAuthorAsList(submissionDto.authorId),
            conference = conferenceService.findById(submissionDto.conferenceId!!),
            topics = findTopicsAsList(submissionDto.topicId),
            comments = "This paper has been submitted and is pending review",
            status = SubmissionStatus.PENDING
        )
    }

    private fun findTopicsAsList(topicId: Long?): MutableList<Topic> {
        if (topicId == null) {
            throw IllegalArgumentException("Topic ID must not be null")
        }
        val topic = topicRepository.findById(topicId).orElseThrow {
            Exception("Topic with ID $topicId not found")
        }
        return mutableListOf(topic)
    }

    private fun findAuthorAsList(authorId: Long?): MutableList<User> {
        if (authorId == null) {
            throw IllegalArgumentException("Author ID must not be null")
        }
        val author = userRepository.findById(authorId).orElseThrow {
            Exception("Author with ID $authorId not found")
        }
        return mutableListOf(author)
    }


}