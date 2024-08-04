//package com.cookbook.life.security
//
//import com.cookbook.life.service.member.UserService
//import io.jsonwebtoken.Jwts
//import io.jsonwebtoken.SignatureAlgorithm
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.GrantedAuthority
//import org.springframework.stereotype.Component
//import java.util.*
//
//@Component
//class JWT (private val userService: UserService) {
//
//    @Value(value = "\${tempoExpiracao}")
//    private var tempoExpiracao: Long = 0
//
//    @Value(value = "\${secret}")
//    private lateinit var secret: String
//
//    fun genareteToken(userName: String, authorities: MutableCollection<out GrantedAuthority>): String {
//        return Jwts.builder()
//            .setSubject(userName)
//            .claim("role", authorities)
//            .setExpiration(Date(System.currentTimeMillis() + tempoExpiracao))
//            .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
//            .compact()
//    }
//
//    fun isValid(token: String?): Boolean {
//        return try {
//            Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(token)
//            true
//        } catch (e: IllegalArgumentException) {
//            false
//        }
//    }
//
//    fun getAuthentication(token: String?) : Authentication {
//        val email = Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(token).body.subject
//        val user = userService.getUserByEmail(email)
//        return UsernamePasswordAuthenticationToken(email, null, user.authorities)
//    }
//}
package  com.cookbook.life.service.member

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
//import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey


@Service
class JWT{
    private val key: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256) // 토큰 암호화키
//    private val key = Keys.hmacShaKeyFor("secretKey".toByteArray(StandardCharsets.UTF_8))
    private val tokenExpirationMsec = 1800000 // 만료시간 30분

    fun createToken(
//        userDetails: UserDetails,
        email: String,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap(),
    ): String {
        val now = Date()
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MILLISECOND, tokenExpirationMsec)
        val expiryDate = calendar.time

        return Jwts.builder()
            .claims()
//            .subject(userDetails.username)
            .subject(email)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(additionalClaims)
            .and()
            .signWith(SignatureAlgorithm.HS256, key)
            .compact()
//        return Jwts.builder()
//            .setSubject("Bonjour Park")
//            .setIssuedAt(now)
//            .setExpiration(expiryDate)
//            .signWith(SignatureAlgorithm.HS256, key)
//            .compact()
    }
}