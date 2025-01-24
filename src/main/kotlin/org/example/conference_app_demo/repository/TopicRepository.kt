package org.example.conference_app_demo.repository

import org.example.conference_app_demo.model.ConferenceCategory
import org.example.conference_app_demo.model.Topic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TopicRepository: JpaRepository<Topic, Long> {
    fun existsByName(name: String): Boolean
    fun findByConferenceCategory(conferenceCategory: ConferenceCategory): List<Topic>
}