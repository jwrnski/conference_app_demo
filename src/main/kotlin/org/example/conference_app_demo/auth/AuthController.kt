package org.example.conference_app_demo.auth

import jakarta.validation.Valid
import org.example.conference_app_demo.dto.UserDto
import org.example.conference_app_demo.model.Role
import org.example.conference_app_demo.service.InstitutionService
import org.example.conference_app_demo.service.UserService
import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/auth")
class AuthController(private val userService: UserService,
                     private val institutionService: InstitutionService,
                     @Lazy private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    fun registerUser(@Valid @ModelAttribute("user") userDto: UserDto, bindingResult: BindingResult, model: Model): String {

        if(bindingResult.hasErrors()) {
            model.addAttribute("institutions", institutionService.findAll())
            model.addAttribute("roles", Role.entries.toTypedArray())
            val titles = listOf("Mr.", "Ms.", "Dr.", "Prof.")
            model.addAttribute("titles", titles)
            return "auth/register"
        }

        val user = userService.toEntity(userDto, passwordEncoder)

        userService.save(user)
        return "redirect:/auth/login"
    }

    @GetMapping("/register")
    fun showRegistrationPage(model: Model): String {
        val institutions = institutionService.findAll()
        model.addAttribute("institutions", institutions)
        model.addAttribute("roles", Role.entries.toTypedArray())
        val titles = listOf("Mr.", "Ms.", "Dr.", "Prof.")
        model.addAttribute("titles", titles)
        model.addAttribute("user", UserDto())
        return "auth/register"
    }

    @PostMapping("/login")
    fun loginUser(@RequestParam email: String, @RequestParam password: String): String {
        val user = userService.findByEmail(email) ?: return "redirect:/auth/login?error=invalid_credentials"

        if(!passwordEncoder.matches(password, user.password)) {
            return "redirect:/auth/login?error=invalid_credentials"
        }

        return "redirect:/conferences"

    }

    @GetMapping("/login")
    fun showLoginPage(model: Model, @RequestParam(required = false) error: String?): String {
        if(error != null)
            model.addAttribute("errorMessage", "Invalid email or password. Please try again.")
        return "auth/login"
    }

}