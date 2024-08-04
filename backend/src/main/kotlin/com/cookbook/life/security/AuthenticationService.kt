//package com.cookbook.life.security
//
//import com.cookbook.life.security.AuthenticationRequest
//import com.cookbook.life.security.AuthenticationResponse
//import com.cookbook.life.service.member.UserService
//
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.stereotype.Service
//import java.util.*
//
//class AuthenticationService(
//    private val authManager: AuthenticationManager,
//) {
//    fun authentication(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
//        authManager.authenticate(
//            UsernamePasswordAuthenticationToken(
//                authenticationRequest.email,
//                authenticationRequest.password
//            )
//        )
//        val user = UserService.loadUserByUsername(authenticationRequest.email)
//
//        val accessToken = createAccessToken(user)
//        val refreshToken = createRefreshToken(user)
//
//        refreshTokenRepository.save(refreshToken, user)
//
//        return AuthenticationResponse(
//            accessToken = accessToken,
//            refreshToken = refreshToken
//        )
//    }
//}
//package  com.cookbook.life.security
//
//import io.jsonwebtoken.Jwts
//import org.springframework.security.core.userdetails.UserDetails
//import java.util.*
//
//class JWT{
//    fun generate(
//        userDetails: UserDetails,
//        expirationDate: Date,
//        additionalClaims: Map<String, Any> = emptyMap()
//    ): String =
//        Jwts.builder()
//            .claims()
//            .subject(userDetails.username)
//            .issuedAt(Date(System.currentTimeMillis()))
//            .expiration(expirationDate)
//            .add(additionalClaims)
//            .and()
//            .signWith("secret")
//            .compact()
//
//}