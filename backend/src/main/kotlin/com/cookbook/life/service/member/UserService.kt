package com.cookbook.life.service.member

import com.cookbook.life.dto.member.SignUpDto
import com.cookbook.life.dto.member.SingInDTO
import com.cookbook.life.dto.member.Token
import com.cookbook.life.model.member.User
import com.cookbook.life.repository.member.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwt: JWT,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
) : UserDetailsService {

    fun singUp(singUpDtd: SignUpDto): User {
        if (userRepository.existsByEmail(singUpDtd.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        return createUser(
            User(
                email = singUpDtd.email,
                passWord = passwordEncoder.encode(singUpDtd.password),
            )
        )
    }

    fun singIn(singInDTO: SingInDTO): Token {
        val user = getUserByEmail(singInDTO.email)
            .takeIf { passwordEncoder.matches(singInDTO.passWord, it.password) }
            ?: throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.")

//        if (!passwordEncoder.matches(singInDTO.passWord, user.passWord)) {
//            throw IllegalArgumentException("Password is incorrect")
//        }

//        val authenticationToken = UsernamePasswordAuthenticationToken(singInDTO.email, singInDTO.passWord)
//        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
//        val token = jwt.createTokenData(user, authentication)

        val token = jwt.createTokenData(user)

        return token
    }

    fun verifyToken(token: String): Boolean {
        return jwt.isExpired(token)
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