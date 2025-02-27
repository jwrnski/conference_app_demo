package org.example.conference_app_demo.dto

import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.example.conference_app_demo.model.ConferenceCategory
import org.example.conference_app_demo.model.Country
import java.time.LocalDate

/**
 * DTO for {@link org.example.conference_app_demo.model.Conference}
 */
data class ConferenceDto(

    var id: Long? = null,
    @field:NotBlank(message = "Name can't be empty!")
    var name: String = "",
    @field:NotBlank(message = "City can't be empty!")
    var city: String = "",
    @field:NotNull(message = "Country can't be empty!")
    var country: Country? = null,
    @field:NotBlank(message = "Description can't be empty!")
    var description: String = "",
    @field:NotNull(message = "Please select start date!")
    @field:FutureOrPresent(message = "Start date must be today or in the future!")
    var startDate: LocalDate? = null,
    @field:NotNull(message = "Please select end date!")
    @field:FutureOrPresent(message = "End date must be today or in the future!")
    var endDate: LocalDate? = null,
    @field:NotNull(message = "Please select category!")
    var category: ConferenceCategory? = null,
)