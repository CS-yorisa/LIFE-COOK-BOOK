package com.cookbook.life.service.member

import com.cookbook.life.dto.member.UserDTO
import com.cookbook.life.model.member.User
import com.cookbook.life.repository.member.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(private val userRepository: UserRepository) {
    fun createUser(user: UserDTO): User {
        var user = User(
                name = user.name,
                email = user.email,
                password = user.password
        )
        return userRepository.save(user)
    }

    fun getUserById(id: UUID): User {
        return userRepository.findById(id).orElseThrow { throw IllegalArgumentException("User not found") }
    }
}