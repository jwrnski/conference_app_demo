package org.example.conference_app_demo.repository

import org.example.conference_app_demo.model.Conference
import org.example.conference_app_demo.model.Registration
import org.example.conference_app_demo.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
@Repository
interface RegistrationRepository: JpaRepository<Registration, Long> {
    fun findByConferenceAndUser(conference: Conference, user: User): Registration?
}