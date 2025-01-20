package org.example.conference_app_demo.controller

import org.example.conference_app_demo.model.Role
import org.example.conference_app_demo.model.User
import org.example.conference_app_demo.service.InstitutionService
import org.example.conference_app_demo.service.UserService
import org.springframework.context.annotation.Lazy
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/auth")
class AuthController(private val userService: UserService,
                     private val institutionService: InstitutionService,
                     @Lazy private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    fun registerUser(
        @RequestParam name: String,
        @RequestParam surname: String,
        @RequestParam title: String,
        @RequestParam email: String,
        @RequestParam password: String,
        @RequestParam phone: String,
        @RequestParam institutionId: Long,
        @RequestParam role: Role
    ): String {
        // Find the selected institution
        val institution = institutionService.findById(institutionId)
        val hashedPassword = passwordEncoder.encode(password)
        println("Fetched institution: $institution")
        // Create and save the new user
        val user = User(
            name = name,
            surname = surname,
            title = title,
            email = email,
            password = hashedPassword,
            phone = phone,
            institution = institution,
            role = role
        )
        userService.save(user)
        println("\nCALLED registerUser $name, $surname, $title, $email, $password, $phone, $institutionId, $role\n")
        // Redirect to a confirmation page or login after successful registration
        return "redirect:/auth/login"
    }


    @PostMapping("/login")
    fun loginUser(@RequestParam email: String, @RequestParam password: String): String {
        val user = userService.findByEmail(email)
        return if(user != null && passwordEncoder.matches(password, user.password)) {
            "redirect:/conferences"
        } else {
            "redirect:/auth/login"
        }
    }

    @GetMapping("/register")
    fun showRegistrationPage(model: Model): String {
        val institutions = institutionService.findAll()
        model.addAttribute("institutions", institutions)
        model.addAttribute("roles", Role.entries.toTypedArray())
        val titles = listOf("Mr.", "Ms.", "Dr.", "Prof.") // Dynamically configurable
        model.addAttribute("titles", titles)
        return "auth/register"
    }

    @GetMapping("/login")
    fun showLoginPage(): String {
        return "auth/login"
    }

}