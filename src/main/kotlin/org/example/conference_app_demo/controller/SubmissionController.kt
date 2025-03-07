package org.example.conference_app_demo.controller

import jakarta.validation.Valid
import org.example.conference_app_demo.dto.SubmissionDto
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
import org.springframework.validation.BindingResult
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
        val loggedInUserId = userService.getLoggedInUserId()
        val conferences = conferenceService.getConferenceByOrganizer(loggedInUserId)
        val submissions = submissionService.getSubmissionsByConferences(conferences)
        model.addAttribute("submissions", submissions)
        model.addAttribute("conferences", conferences)

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

        val authorId = userService.getLoggedInUserId()
        model.addAttribute("authorId", authorId)

        val comments = "This submission is under review."
        model.addAttribute("comments", comments)

        val topics = topicRepository.findByConferenceCategory(conference.category)
        model.addAttribute("topics", topics)

        val submission = SubmissionDto(authorId = authorId)
        model.addAttribute("submission", submission)

        return "submission/submission-form"
    }

    @PostMapping
    fun createSubmission(@Valid @ModelAttribute("submission") submissionDto: SubmissionDto,
                         bindingResult: BindingResult,
                         model: Model,
                         ): String{

        if(bindingResult.hasErrors()){
            bindingResult.allErrors.forEach {error -> println("\nValidation error: ${error.defaultMessage}\n") }
            val conference = conferenceService.findById(submissionDto.conferenceId!!)
            model.addAttribute("conferenceName", conference.name)
            model.addAttribute("conferenceId", submissionDto.conferenceId)
            model.addAttribute("submission", submissionDto)
            model.addAttribute("authorId", submissionDto.authorId)
            model.addAttribute("topics", topicRepository.findByConferenceCategory(conference.category))
            return "submission/submission-form"
        }

        val conferenceId = submissionDto.conferenceId!!

        val submission = submissionService.toEntity(submissionDto)
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
                     @RequestParam status: SubmissionStatus,
                     @RequestParam(required = false) comments: String?): String {
        submissionService.updateStatus(submissionId, status, comments)
        return "redirect:/submissions/$submissionId"
    }

}