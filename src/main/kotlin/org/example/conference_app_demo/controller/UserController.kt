package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.User
import org.example.conference_app_demo.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun findAllUsers(): ResponseEntity<List<User>> {
        val users = userService.findAll()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        val user = userService.findById(id)
        return ResponseEntity.ok(user)
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

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long,@RequestBody user: User): ResponseEntity<User> {
        val updatedUser = userService.update(id, user)
        return ResponseEntity.ok(updatedUser)
    }
}