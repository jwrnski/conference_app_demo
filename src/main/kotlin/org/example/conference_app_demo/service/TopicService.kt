package org.example.conference_app_demo.service

import org.example.conference_app_demo.repository.TopicRepository
import org.springframework.stereotype.Service

@Service
class TopicService(private val topicRepository: TopicRepository,
                   private val conferenceService: ConferenceService) {


}