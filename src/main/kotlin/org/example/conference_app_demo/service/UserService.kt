package org.example.conference_app_demo.service

import org.example.conference_app_demo.dto.UserDTO
import org.example.conference_app_demo.model.User
import org.example.conference_app_demo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {


    fun findByIds(ids: List<Long>): MutableList<User> {
        return userRepository.findAllById(ids)
    }


    fun toDTO(user: User): UserDTO {
        return UserDTO(
            id = user.id,
            name = user.name,
            surname = user.surname,
            title = user.title,
            email = user.email,
            password = "",
            phone = user.phone,
            institution = user.institution,
            role = user.role,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        )
    }

    fun toEntity(dto: UserDTO): User {
        return User(
            id = dto.id,
            name = dto.name,
            surname = dto.surname,
            title = dto.title,
            email = dto.email,
            password = "", // Password should be managed separately (e.g., hashed and set during registration)
            phone = dto.phone,
            institution = dto.institution,
            role = dto.role,
            createdAt = dto.createdAt,
            updatedAt = dto.updatedAt
        )
    }
}