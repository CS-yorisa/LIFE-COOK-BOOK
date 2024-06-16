package com.cookbook.life.service.member

import com.cookbook.life.model.member.User
import com.cookbook.life.repository.member.UserRepository
import com.cookbook.life.dto.member.SignUpDto
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
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