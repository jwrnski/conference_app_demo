package org.example.conference_app_demo.controller

import org.example.conference_app_demo.service.TopicService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/topics")
class TopicController(private val topicService: TopicService) {


}