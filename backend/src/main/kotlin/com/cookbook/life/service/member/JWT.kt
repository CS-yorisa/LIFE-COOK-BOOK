package  com.cookbook.life.service.member

import com.cookbook.life.dto.member.CustomPrincipal
import com.cookbook.life.dto.member.Token
import com.cookbook.life.model.member.User
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.SecretKey

@Service
class JWT {
    private val key: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256) // 토큰 암호화키

    //    private val key = Keys.hmacShaKeyFor("secretKey".toByteArray(StandardCharsets.UTF_8))
    private val tokenExpirationMsec = 1800000 // 만료시간 30분

    fun createTokenData(user: User): Token {
        return createTokenInfo(user.username)
    }

    private fun createTokenInfo(email: String): Token {
//        val authorites: String = authentication
//            .authorities
//            .joinToString(",", transform = GrantedAuthority::getAuthority)

        val accessExpireMilliseconds: Long = 60 * 60 * 1000
        val refreshExpireMilliseconds: Long = 60 * 60 * 24 * 7 * 1000

        val now = Date()
        val accessExp = Instant.now().plus(6, ChronoUnit.HOURS)
        val accessExpiration = Date(now.time + accessExpireMilliseconds)
        val refreshExpiration = Date(now.time + refreshExpireMilliseconds)

        println(now)
        println(accessExpiration)
        // Access Token
        val accessToken = Jwts
            .builder()
            .subject(email)
//            .claim("auth", authorites)
//            .expiration(accessExpiration)
            .expiration(Date.from(accessExp))
            .issuedAt(now)
            .signWith(key, Jwts.SIG.HS256)
//            .signWith(SignatureAlgorithm.HS256, key)
            .compact()

        // Refresh Token
        val refreshToken = Jwts
            .builder()
            .subject(email)
//            .claim("auth", authorites)
            .expiration(refreshExpiration)
            .issuedAt(now)
            .signWith(key, Jwts.SIG.HS256)
//            .signWith(SignatureAlgorithm.HS256, key)
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
        return getAllClaims(token)
            .expiration
            .after(Date(System.currentTimeMillis()))
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    /** token 정보 추출 */
    fun getAuthentication(token: String): Authentication {
        val claims: Claims = getClaims(token)

        val auth = claims["auth"] ?: throw RuntimeException("잘못된 토큰입니다.")
        /* 권한 정보 추출 */
        val authorities = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }

        val principal = CustomPrincipal(
            userId = claims.subject,
            nick = "",
            email = claims.subject,
            password = null,
            authorities = authorities,
        )
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
        Jwts.parser()
            .verifyWith(key) // <----
            .build()
            .parseSignedClaims(token)
            .payload

    fun validateTokenAndGetSubject(token: String?): String? = Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .payload
        .subject
}