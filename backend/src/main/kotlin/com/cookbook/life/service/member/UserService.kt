package com.cookbook.life.service.member

import com.cookbook.life.model.member.User
import com.cookbook.life.repository.member.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    fun getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow { throw IllegalArgumentException("User not found") }
    }
}