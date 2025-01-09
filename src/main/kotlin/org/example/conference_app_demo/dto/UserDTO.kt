package org.example.conference_app_demo.dto

import org.example.conference_app_demo.model.Role
import java.io.Serializable
import java.time.LocalDateTime

/**
 * DTO for {@link org.example.conference_app_demo.model.User}
 */
data class UserDTO(
    val id: Long = 0,
    val name: String,
    val surname: String,
    val title: String,
    val email: String,
    val password: String,
    val phone: String,
    val institution: String,
    val role: Role,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) : Serializable