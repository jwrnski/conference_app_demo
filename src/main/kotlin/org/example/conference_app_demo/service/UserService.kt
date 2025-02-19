package org.example.conference_app_demo.service

import org.example.conference_app_demo.model.User
import org.example.conference_app_demo.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UserService(private val userRepository: UserRepository) {


    fun findByIds(ids: List<Long>): MutableList<User> {
        return userRepository.findAllById(ids)
    }

    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun findById(id: Long): User {
        return userRepository.findById(id).orElseThrow { Exception("User with id $id not found") }
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun getRegistrationId(){

    }

    fun deleteById(id: Long) {
        if(userRepository.findById(id).isEmpty) throw Exception()
        userRepository.deleteById(id)
    }

    @Transactional
    fun save(user: User): User {
        return userRepository.save(user)
    }

    fun update(id: Long, user: User): User {
        val existingUser = findById(id) ?: throw Exception("User with id $id not found")
        existingUser.name = user.name
        existingUser.surname = user.surname
        existingUser.title = user.title
        existingUser.email = user.email
        existingUser.phone = user.phone
        existingUser.institution = user.institution
        existingUser.role = user.role
        existingUser.updatedAt = LocalDateTime.now()
        return userRepository.save(existingUser)
    }


    /*fun toDTO(user: User): UserDTO {
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
    }*/
}