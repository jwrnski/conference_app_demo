package org.example.conference_app_demo.config

import org.example.conference_app_demo.service.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig(private val customUserDetailsService: CustomUserDetailsService) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { it
                .requestMatchers("/", "/auth/register", "auth/login", "/css/**", "/js/**", "/images/**", "/conferences", "/presentations", "/institutions/**").permitAll() // Public pages (registration, login, static files)
                .requestMatchers("/**").hasRole("ADMIN") // Admin-only access
                .anyRequest().authenticated() // Require authentication for everything else
            }
            .csrf { it.disable() }
            .formLogin { it
                .loginPage("/auth/login") // Custom login page
                .defaultSuccessUrl("/", true) // Redirect to home after successful login
                .usernameParameter("email")
                .permitAll() // Allow anyone to access the login page
            }
            .logout { it
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/") // Redirect to home after logout
                .permitAll() // Allow logout for everyone
            }
        return http.build()
    }

    /*@Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        val authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        authManagerBuilder
            .userDetailsService(customUserDetailsService) // Set the custom user details service
            .passwordEncoder(passwordEncoder()) // Add the password encoder
        return authManagerBuilder.build()
    }*/


    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}