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
            // .requestMatchers(EndPoint.AUTH_ROOT_PATH, EndPoint.SIGN_UP, EndPoint.SIGN_IN).permitAll()
//            .logout { logout: LogoutConfigurer<HttpSecurity?> ->
//                logout
//                    .logoutSuccessUrl("/login")
//                    .invalidateHttpSession(true)
//            }
            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity?> ->
                session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
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

}