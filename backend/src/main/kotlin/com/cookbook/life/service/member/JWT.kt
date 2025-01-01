package  com.cookbook.life.service.member

import com.cookbook.life.dto.member.Token
import com.cookbook.life.model.member.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.SecretKey

@Service
class JWT {
    private val key: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256) // 토큰 암호화키

    fun createTokenData(user: User): Token {
        return createTokenInfo("${user.username}:${user.type}")
    }

    private fun createTokenInfo(email: String): Token {
        val now = Date()
        val accessExp = Instant.now().plus(6, ChronoUnit.HOURS)
        val refreshExp = Instant.now().plus(7, ChronoUnit.DAYS)

        // Access Token
        val accessToken = Jwts
            .builder()
            .subject(email)
            .expiration(Date.from(accessExp))
            .issuedAt(now)
            .signWith(key, Jwts.SIG.HS256)
            .compact()

        // Refresh Token
        val refreshToken = Jwts
            .builder()
            .subject(email)
            .expiration(Date.from(refreshExp))
            .issuedAt(now)
            .signWith(key, Jwts.SIG.HS256)
            .compact()

        return Token(accessToken, refreshToken)
    }

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

    fun validateTokenAndGetSubject(token: String?): String? = Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .payload
        .subject
}