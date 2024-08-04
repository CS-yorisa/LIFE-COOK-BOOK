package com.cookbook.life.service.member

import com.cookbook.life.model.member.User
import com.cookbook.life.repository.member.UserRepository
import com.cookbook.life.dto.member.SignUpDto
import com.cookbook.life.dto.member.SingInDTO
import com.cookbook.life.dto.member.Token
import com.cookbook.life.service.member.JWT
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwt: JWT,
): UserDetailsService {

    fun singUp(singUpDtd: SignUpDto): User {
        if (userRepository.existsByEmail(singUpDtd.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        return createUser(User(
            email = singUpDtd.email,
//            passWord = singUpDtd.password,
            passWord = passwordEncoder.encode(singUpDtd.password),
        ))
    }

    fun singIn(singInDTO: SingInDTO): Token {
        val user = loadUserByUsername(singInDTO.email)
        if (!passwordEncoder.matches(singInDTO.password, user.password)) {
            throw IllegalArgumentException("Password is incorrect")
        }

        val expirationDate: Date = Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)
        val access = jwt.createToken(email=user.email, expirationDate = expirationDate)
//        print(access)
        // 우선 같은 토큰으로 access, refresh 둘 다 반환
        return Token(access,access)
    }

    override fun loadUserByUsername(email: String) =
        userRepository.findByEmail(email) ?: throw EntityNotFoundException("Username: $email not found.")

    fun createUser(user: User): User {
        return userRepository.save(user)
    }

//    fun getUserById(id: UUID): User {
//        return userRepository.findById(id) ?: throw IllegalArgumentException("User not found")
//    }

    fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw IllegalArgumentException("User not found")
    }
}