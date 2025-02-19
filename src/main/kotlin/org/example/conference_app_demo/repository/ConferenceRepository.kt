package org.example.conference_app_demo.repository

import org.example.conference_app_demo.model.Conference
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConferenceRepository : JpaRepository<Conference, Long> {
    fun existsByName(name: String): Boolean
}