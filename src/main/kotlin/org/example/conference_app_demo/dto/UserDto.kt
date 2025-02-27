package org.example.conference_app_demo.dto

import jakarta.validation.constraints.*
import org.example.conference_app_demo.model.Role

/**
 * DTO for {@link org.example.conference_app_demo.model.User}
 */
data class UserDto(
    var id: Long? = null,
    @field:NotBlank(message = "Name can't be empty!")
    var name: String = "",
    @field:NotBlank(message = "Surname can't be empty!")
    var surname: String = "",
    @field:NotNull(message = "Please select title")
    var title: String = "",
    @field:NotBlank(message = "Email can't be empty!")
    var email: String = "",
    @field:NotBlank(message = "Password can't be empty!")
    @field:Size(min = 8, message = "Password must be at least 8 characters long!")
    var password: String = "",
    @field:NotBlank(message = "Phone can't be empty!")
    var phone: String = "",
    @field:NotNull(message = "Please select role")
    var role: Role? = null,
    @field:NotNull(message = "Please select institution")
    var institutionId: Long? = null,
)