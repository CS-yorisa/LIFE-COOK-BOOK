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


//@Configuration
//@EnableWebSecurity
//open class SecurityConfig {
//
//    @Bean
//    open fun web(http: HttpSecurity): SecurityFilterChain {
//        http {
//            securityMatcher("/api/**")
//            authorizeHttpRequests {
//                authorize("/user/**", hasRole("USER"))
//                authorize("/admin/**", hasRole("ADMIN"))
//                authorize(anyRequest, authenticated)
//            }
//        }
//        return http.build()
//    }
//
//}

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig(
    private val jwtTokenProvider: JWT,
//    private val passwordEncoder: PasswordEncoder,
//    private val customUserDetailsService: UserService,
) {

    // 스프링 시큐리티 기능 비활성화 (H2 DB 접근을 위해)
    //	@Bean
    //	public WebSecurityCustomizer configure() {
    //		return (web -> web.ignoring()
    //				.requestMatchers(toH2Console())
    //				.requestMatchers("/h2-console/**")
    //		);
    //	}
    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    @Throws(Exception::class)
    fun filterChain(
        http: HttpSecurity,
//        jwtAuthenticationFilter: JWT
    ): SecurityFilterChain {
        http.csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
//            .csrf().disable()
            .httpBasic { obj: HttpBasicConfigurer<HttpSecurity> -> obj.disable() }
            .formLogin { obj: FormLoginConfigurer<HttpSecurity> -> obj.disable() }
            .authorizeHttpRequests(Customizer { authorize ->
                authorize
                    .requestMatchers("/member/signup", "/member/signin").permitAll()
                    .anyRequest().authenticated()
            })
            // .requestMatchers(EndPoint.AUTH_ROOT_PATH, EndPoint.SIGN_UP, EndPoint.SIGN_IN).permitAll()
            .logout { logout: LogoutConfigurer<HttpSecurity?> ->
                logout
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
            }
            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity?> ->
                session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
//            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

//    @Bean
//    fun authenticationManager(): AuthenticationManager {
//        val provider = DaoAuthenticationProvider()
//        provider.setPasswordEncoder(passwordEncoder)
//        provider.setUserDetailsService(customUserDetailsService)
//        return ProviderManager(provider)
//    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

}