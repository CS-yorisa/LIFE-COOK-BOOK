package com.cookbook.life.security

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
                .httpBasic { it.disable() }
                .formLogin { it.disable() }
                .authorizeHttpRequests {
                    it.requestMatchers("/", "/member/*", "/index.html", "/favicon.ico").permitAll()
                    it.anyRequest().authenticated()
                }
                .sessionManagement {
                    it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
        return http.build()
    }
}