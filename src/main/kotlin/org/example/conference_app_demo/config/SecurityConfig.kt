package org.example.conference_app_demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { it
                .requestMatchers("/**", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().permitAll()
            }
            .csrf { it.disable() }
            /*.formLogin { it
                .loginPage("/login") // Custom login page
                .defaultSuccessUrl("/", true) // Redirect to homepage after log in
                .permitAll() // Allow anyone to access the login page
            }
            .logout { it
                .logoutUrl("/logout")
                .logoutSuccessUrl("/") // Redirect to homepage after logout
                .permitAll() // Allow anyone to log out
            }*/
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user: UserDetails = User
            .withUsername("admin")
            .password("{noop}admin")
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }
}