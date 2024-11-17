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

//import io.jsonwebtoken.security.Keys
import com.cookbook.life.dto.member.Token
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JWT {
    private val key: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256) // 토큰 암호화키

    //    private val key = Keys.hmacShaKeyFor("secretKey".toByteArray(StandardCharsets.UTF_8))
    private val tokenExpirationMsec = 1800000 // 만료시간 30분

    //    fun createTokenData(authentication: Authentication): Token {
    fun createTokenData(user: com.cookbook.life.model.member.User): Token {
//        val customPrincipal = authentication.principal as CustomPrincipal
//        val authorities: String = customPrincipal
//            .authorities
//            .joinToString(",", transform = GrantedAuthority::getAuthority)
        return createTokenInfo(user.email, "")
    }

    private fun createTokenInfo(email: String, authorities: String): Token {
        val accessExpireMilliseconds: Long = 60 * 60 * 1000
        val refreshExpireMilliseconds: Long = 60 * 60 * 24 * 7 * 1000

        val now = Date()
        val accessExpiration = Date(now.time + accessExpireMilliseconds)
        val refreshExpiration = Date(now.time + refreshExpireMilliseconds)

        println(now)
        println(accessExpiration)
        println(now.time + accessExpireMilliseconds)
        // Access Token
        val accessToken = Jwts
            .builder()
            .subject(email)
            .claim("auth", authorities)
            .expiration(accessExpiration)
            .issuedAt(now)
            .signWith(SignatureAlgorithm.HS256, key)
            .compact()

        // Refresh Token
        val refreshToken = Jwts
            .builder()
            .subject(email)
            .claim("auth", authorities)
            .expiration(refreshExpiration)
            .issuedAt(now)
            .signWith(SignatureAlgorithm.HS256, key)
            .compact()

        return Token(accessToken, refreshToken)
    }

    fun createToken(
//        userDetails: UserDetails,
        email: String,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap(),
        authentication: Authentication,
    ): String {
        return Jwts.builder()
//            .subject(userDetails.username)
            .claims()
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

    fun isValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token)

        return userDetails.username == email && !isExpired(token)
    }

    fun extractEmail(token: String): String? =
        getAllClaims(token)
            .subject

    fun isExpired(token: String): Boolean {
        println(1111111)
        println(getAllClaims(token))
        return getAllClaims(token)
            .expiration
            .before(Date(System.currentTimeMillis()))
    }

    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser()
            .verifyWith(key)
            .build()

        return parser
            .parseSignedClaims(token)
            .payload
    }

    /** token 정보 추출 */
    fun getAuthentication(token: String): Authentication {
        val claims: Claims = getClaims(token)
        println(claims)

        val auth = claims["auth"] ?: throw RuntimeException("잘못된 토큰입니다.")
        println(auth)
        /* 권한 정보 추출 */
        val authorities = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }
        println(authorities)

        println(3333)
        val principal: UserDetails = User(claims.subject, "", authorities)
        println(4444)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    /** Token 검증 */
    fun validateToken(token: String): Boolean {
        try {
//            println(token)
            getClaims(token)
//            println(getClaims(token))
            return true
        } catch (e: Exception) {
            when (e) {
                is SecurityException -> {}  // Invalid JWT Token
                is MalformedJwtException -> {}  // Invalid JWT Token
                is ExpiredJwtException -> {}    // Expired JWT Token
                is UnsupportedJwtException -> {}    // Unsupported JWT Token
                is IllegalArgumentException -> {}   // JWT claims string is empty
                else -> {}  // else
            }
            println(e.message)
        }
        return false
    }

    private fun getClaims(token: String): Claims =
//        Jwts.parserBuilder()
//        Jwts.parser()
//            .setSigningKey(key)
//            .build()
//            .parseClaimsJws(token)
//            .body
        Jwts.parser()
            .verifyWith(key) // <----
            .build()
            .parseSignedClaims(token)
            .payload
//            .parseEncryptedClaims(token)
}