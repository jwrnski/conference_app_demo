package org.example.conference_app_demo.service

import org.example.conference_app_demo.auth.CustomUserDetails
import org.example.conference_app_demo.model.User
import org.example.conference_app_demo.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user: User = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User with email $email not found.")

        // Return CustomUserDetails with user ID and roles
        return CustomUserDetails(
            id = user.id, // Pass the user's ID
            email = user.email,
            password = user.password,
            authorities = listOf(SimpleGrantedAuthority("ROLE_${user.role.name}")) // Grant roles
        )
    }
}