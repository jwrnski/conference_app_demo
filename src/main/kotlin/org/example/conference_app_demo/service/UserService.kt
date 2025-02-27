package org.example.conference_app_demo.service

import org.example.conference_app_demo.dto.UserDto
import org.example.conference_app_demo.model.User
import org.example.conference_app_demo.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepository: UserRepository,
    private val institutionService: InstitutionService
) {


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

    fun getLoggedInUserId(): Long{
        val authenticatedUser = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("No authenticated user found in security context")
        val principal = authenticatedUser.principal
        if (principal is org.example.conference_app_demo.auth.CustomUserDetails) {
            return principal.getId()
        }
        throw IllegalStateException("Unable to retrieve user ID: Principal is not an instance of CustomUserDetails")
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

    fun toEntity(userDto: UserDto, passwordEncoder: PasswordEncoder): User {
        return User(
            name = userDto.name,
            surname = userDto.surname,
            title = userDto.title,
            email = userDto.email,
            phone = userDto.phone,
            institution = institutionService.findById(userDto.institutionId!!),
            password = passwordEncoder.encode(userDto.password),
            role = userDto.role!!
        )
    }

    fun toDto(){

    }
}