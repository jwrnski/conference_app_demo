package org.example.conference_app_demo.service

import org.example.conference_app_demo.service.UserService
import org.example.conference_app_demo.model.Conference
import org.example.conference_app_demo.model.Registration
import org.example.conference_app_demo.model.User
import org.example.conference_app_demo.repository.ConferenceRepository
import org.example.conference_app_demo.repository.RegistrationRepository
import org.example.conference_app_demo.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RegistrationService(
    private val registrationRepository: RegistrationRepository,
    private val conferenceRepository: ConferenceRepository,
    private val userRepository: UserRepository,
    private val userService: UserService,
) {

    fun registerUser(conferenceId: Long, userId: Long) {
        val conference: Conference = conferenceRepository.findById(conferenceId)
            .orElseThrow { IllegalArgumentException("Conference not found") }

        val user: User = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        val existingRegistration = registrationRepository.findByConferenceAndUser(conference, user)
        if (existingRegistration != null && existingRegistration.active) {
            throw IllegalArgumentException("User is already registered for this conference")
        }

        val registration = Registration(
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            conference = conference,
            user = user
        )

        registrationRepository.save(registration)
    }

    fun unregisterUser(conferenceId: Long, userId: Long) {
        val conference = conferenceRepository.findById(conferenceId)
            .orElseThrow { IllegalArgumentException("Conference not found") }
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }
        val registration = registrationRepository.findByConferenceAndUser(conference, user)
            ?: throw IllegalArgumentException("Registration not found")
        registration.active = false
        registrationRepository.save(registration)
    }

    fun isUserRegistered(conferenceId: Long, userId: Long): Boolean {
        val conference: Conference = conferenceRepository.findById(conferenceId)
            .orElseThrow { IllegalArgumentException("Conference not found") }
        val user: User = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }
        return registrationRepository.findByConferenceAndUser(conference, user) != null
    }

}