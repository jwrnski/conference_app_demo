package org.example.conference_app_demo.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.example.conference_app_demo.model.SubmissionStatus
import org.example.conference_app_demo.model.Topic
import java.io.Serializable
import java.time.LocalDateTime

/**
 * DTO for {@link org.example.conference_app_demo.model.Submission}
 */
data class SubmissionDto(

    var id: Long? = null,

    @field:NotBlank(message = "Paper title can't be empty!")
    var paperTitle: String = "",
    @field:NotBlank(message = "Paper abstract can't be empty!")
    var abstract: String = "",
    @field:NotBlank(message = "Select a file!")
    var filePath: String = "",
    @field:NotNull(message = "Please select an author!")
    var authorId: Long? = null,
    @field:NotNull(message = "Please select a conference!")
    var conferenceId: Long? = null,
    @field:NotNull(message = "Please select a topic!")
    var topicId: Long? = null,
)