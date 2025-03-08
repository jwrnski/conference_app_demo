package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.User
import org.example.conference_app_demo.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun findAllUsers(): ResponseEntity<List<User>> {
        val users = userService.findAll()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/my-account")
    fun myAccountPage(model: Model): String {
        val userId = userService.getLoggedInUserId()
        val user = userService.findById(userId)
        model.addAttribute("user", user)
        val activeRegistrations = user.registrations.filter { it.active }
        model.addAttribute("registrations", activeRegistrations)
        val submissions = user.submissions
        model.addAttribute("submissions", submissions)
        return "user/my-account"
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long, model: Model): String {
        val user = userService.findById(id)
        model.addAttribute("user", user)
        return "user/user-details"
    }

    @PostMapping
    fun createUser(user: User): ResponseEntity<User> {
        val savedUser = userService.save(user)
        return ResponseEntity.status(201).body(savedUser)
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable id: Long): ResponseEntity<Void> {
        userService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/edit-user")
    fun editUserPage(model: Model): String {
        val userId = userService.getLoggedInUserId()
        val user = userService.findById(userId)
        model.addAttribute("user", user)
        return "user/edit-user"
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long,@RequestBody user: User): ResponseEntity<User> {
        val updatedUser = userService.update(id, user)
        return ResponseEntity.ok(updatedUser)
    }
}