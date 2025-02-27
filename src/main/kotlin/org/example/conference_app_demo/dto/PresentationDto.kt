package org.example.conference_app_demo.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.example.conference_app_demo.model.Schedule
import java.io.Serializable
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * DTO for {@link org.example.conference_app_demo.model.Presentation}
 */
data class PresentationDto(

    var id: Long? = null,
    @field:NotBlank(message = "Title can't be empty!")
    var title: String? = "",
    @field:NotBlank(message = "Description can't be empty!")
    var description: String? = "",
    @field:NotNull(message = "Please select a start time!")
    var startTime: LocalTime? = null,
    @field:NotNull(message = "Please select an end time!")
    var endTime: LocalTime? = null,
    @field:NotNull
    var conferenceId: Long? = null,
    @field:NotNull
    var scheduleId: Long? = null,
    @field:NotNull(message = "Please select a submission!")
    var submissionId: Long? = null,
    @field:NotNull(message = "Please select a speaker!")
    var speakerId: Long? = null,
)