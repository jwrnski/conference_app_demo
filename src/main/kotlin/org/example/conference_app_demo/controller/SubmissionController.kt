package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.Submission
import org.example.conference_app_demo.model.SubmissionStatus
import org.example.conference_app_demo.model.Topic
import org.example.conference_app_demo.model.User
import org.example.conference_app_demo.repository.TopicRepository
import org.example.conference_app_demo.service.ConferenceService
import org.example.conference_app_demo.service.SubmissionService
import org.example.conference_app_demo.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/submissions")
class SubmissionController(
    private val submissionService: SubmissionService,
    private val conferenceService: ConferenceService,
    private val userService: UserService,
    private val topicRepository: TopicRepository
) {

    @GetMapping
    fun getAllSubmissions(model: Model): String {
        val submissions = submissionService.findAll()
        model.addAttribute("submissions", submissions)
        return "submission/submissions"
    }

    @GetMapping("/{id}")
    fun getSubmissionById(@PathVariable id: Long, model: Model): String {
        val submission = submissionService.findById(id)
        model.addAttribute("submission", submission)
        return "submission/submission-details"
    }

    @GetMapping("/submission-form")
    fun getSubmissionFormPage(@RequestParam("conferenceId") conferenceId: Long, model: Model): String{
        val conference = conferenceService.findById(conferenceId)
        model.addAttribute("conferenceId", conference.id)
        val conferenceName = conferenceService.getNameById(conferenceId)
        model.addAttribute("conferenceName", conferenceName)
        val authenticatedUser = SecurityContextHolder.getContext().authentication
        val principal = authenticatedUser.principal
        if (principal is org.example.conference_app_demo.auth.CustomUserDetails) {
            val userId = principal.getId()
            model.addAttribute("userId", userId)
        }
        val comments = "This submission is under review."
        model.addAttribute("comments", comments)
        val topics = topicRepository.findByConferenceCategory(conference.category)
        model.addAttribute("topics", topics)
        return "submission/submission-form"
    }

    @PostMapping
    fun createSubmission(@ModelAttribute submission: Submission,
                         @RequestParam("conferenceId") conferenceId: Long,
                         @RequestParam("userId") user: User,
                         @RequestParam("comments") comments: String,
                         @RequestParam("topics") selectedTopics: List<Long>): String{
        val conference = conferenceService.findById(conferenceId)
        submission.conference = conference
        val author = userService.findById(user.id)
        submission.authors.add(author)
        submission.comments = comments
        val topics = topicRepository.findAllById(selectedTopics)
        submission.topics = topics.toMutableList()
        submissionService.save(submission)
        return "redirect:/conferences/$conferenceId"
    }

    @DeleteMapping("/{id}")
    fun deleteSubmissionById(@PathVariable id: Long): ResponseEntity<Void> {
        submissionService.deleteById(id)
        return ResponseEntity.noContent().build()
    }


    @PutMapping()
    fun updateStatus(@RequestParam submissionId: Long,
                     @RequestParam status: SubmissionStatus): String {
        submissionService.updateStatus(submissionId, status)
        return "redirect:/submissions/$submissionId"
    }

}