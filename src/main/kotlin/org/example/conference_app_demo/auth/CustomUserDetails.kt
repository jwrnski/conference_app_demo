package org.example.conference_app_demo.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val id: Long,                // Include user ID
    private val email: String,           // Use email as username
    private val password: String,
    private val authorities: Collection<GrantedAuthority>
) : UserDetails {

    fun getId(): Long = id // Expose the user's ID as a method

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
    override fun getPassword(): String = password
    override fun getUsername(): String = email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}