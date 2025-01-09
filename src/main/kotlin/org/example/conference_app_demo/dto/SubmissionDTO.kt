package org.example.conference_app_demo.dto

import java.io.Serializable
import java.time.LocalDateTime

/**
 * DTO for {@link org.example.conference_app_demo.model.Submission}
 */
data class SubmissionDTO(
    val id: Long = 0,
    val abstractTitle: String,
    val content: String,
    val author: String,
    val conference: String,
    val topic: String,
    val status: String,
    val rating: Int,
    val comments: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) : Serializable