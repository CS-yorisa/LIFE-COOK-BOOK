package com.cookbook.life

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


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
class SecurityConfig {

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
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .httpBasic { obj: HttpBasicConfigurer<HttpSecurity> -> obj.disable() }
            .formLogin { obj: FormLoginConfigurer<HttpSecurity> -> obj.disable() }
            .authorizeHttpRequests(Customizer { authorize ->
                authorize
                    .requestMatchers("/", "/member/*").permitAll()
                    .anyRequest().authenticated()
            }) // 폼 로그인은 현재 사용하지 않음
            //				.formLogin(formLogin -> formLogin
            //						.loginPage("/login")
            //						.defaultSuccessUrl("/home"))
            .logout { logout: LogoutConfigurer<HttpSecurity?> ->
                logout
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
            }
            .sessionManagement { session: SessionManagementConfigurer<HttpSecurity?> ->
                session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
        return http.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}