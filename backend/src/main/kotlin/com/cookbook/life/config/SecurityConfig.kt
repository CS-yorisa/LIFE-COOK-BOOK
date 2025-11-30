package com.cookbook.life

import com.cookbook.life.security.JwtAuthenticationFilter
import com.cookbook.life.service.member.JWT
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig(
    private val jwtTokenProvider: JWT,
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(
        http: HttpSecurity,
//        jwtAuthenticationFilter: JWT
    ): SecurityFilterChain {
        http
            .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
//            .csrf { it.disable() }
            .httpBasic { obj: HttpBasicConfigurer<HttpSecurity> -> obj.disable() }
            .formLogin { obj: FormLoginConfigurer<HttpSecurity> -> obj.disable() }
            .authorizeHttpRequests(Customizer { authorize ->
                authorize
//                        .anyRequest().permitAll()
                    .requestMatchers("/member/signup", "/member/signin", "/member/verify", "/error").permitAll()
                    .anyRequest().authenticated()
            })
            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity?> ->
                session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .cors { }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    // 위 설정에서 .cors{}를 해주어야만 아래 cors 설정이 적용된다.
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        // 허용할 URL들
        configuration.allowedOrigins = listOf("http://localhost:3000")
        // 허용할 Method들
        configuration.allowedMethods = listOf("POST", "GET", "DELETE", "PUT")
        // 허용할 Header들
        configuration.allowedHeaders = listOf("*")
        // 세션 쿠키를 유지할 것 인지
        configuration.allowCredentials = true

        // cors 적용
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}