package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.Presentation
import org.example.conference_app_demo.service.*
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/presentations")
class PresentationController(
    private val presentationService: PresentationService,
    private val conferenceService: ConferenceService,
    private val userService: UserService,
    private val submissionService: SubmissionService,
    private val scheduleService: ScheduleService
) {

    @GetMapping
    fun getAllPresentations(model: Model): String {
        val presentations = presentationService.findAll();
        model.addAttribute("presentations", presentations);
        return "presentation/presentations";
    }

    @GetMapping("/{id}")
    fun getPresentationById(@PathVariable id: Long, model: Model): String {
        val presentation = presentationService.findById(id);
        val authors = presentation.speakers.size
        model.addAttribute("presentation", presentation);
        return "presentation/presentation-details";
    }

    @GetMapping("/create-presentation")
    fun createPresentationPage(@RequestParam conferenceId: Long,
                               @RequestParam(required = false) scheduleId: Long?,
                               model: Model): String {
        model.addAttribute("conferenceId", conferenceId);
        val conferenceName = conferenceService.getNameById(conferenceId)
        model.addAttribute("conferenceName", conferenceName)
        val authors = userService.findAll();
        model.addAttribute("authors", authors)
        val submissions = submissionService.getApprovedSubmissions()
        model.addAttribute("submissions", submissions)
        val schedules = scheduleService.findByConferenceId(conferenceId)
        model.addAttribute("schedules", schedules)
        if (scheduleId != null) {
            val selectedSchedule = scheduleService.findById(scheduleId)
            model.addAttribute("selectedSchedule", selectedSchedule)
        } else {
            model.addAttribute("selectedSchedule", null)
        }
        return "presentation/create-presentation"
    }

    @GetMapping("/edit/{id}")
    fun getEditPresentationPage(@PathVariable id: Long, model: Model): String {
        val presentation = presentationService.findById(id)
        model.addAttribute("presentation", presentation)
        val authors = userService.findAll();
        model.addAttribute("authors", authors)
        return "presentation/edit-presentation"
    }

    @PostMapping
    fun createPresentation(@ModelAttribute presentation: Presentation,
                           @RequestParam("conferenceId") conferenceId: Long,
                           @RequestParam("submissionId") submissionId: Long,
                           @RequestParam("authorId") authorId: Long,
                           @RequestParam("scheduleId") scheduleId: Long
                            ): String {
        val conference = conferenceService.findById(conferenceId)
        presentation.conference = conference

        val submission = submissionService.findById(submissionId)
        if (submission != null) {
            presentation.submission = submission
        }

        val author = userService.findById(authorId)
        presentation.speakers.add(author)

        val schedule = scheduleService.findById(scheduleId)
        presentation.schedule = schedule

        presentationService.save(presentation)
        return "redirect:/conferences/$conferenceId"
    }

    @DeleteMapping("/{id}")
    fun deletePresentationById(@PathVariable id: Long): ResponseEntity<Presentation> {
        presentationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit/{id}")
    fun updatePresentation(@PathVariable id: Long, @RequestBody presentation: Presentation): ResponseEntity<Presentation>{
        val updatedPresentation = presentationService.update(id, presentation);
        return ResponseEntity.ok(updatedPresentation);
    }
}