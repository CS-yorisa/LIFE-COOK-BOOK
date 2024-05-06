package com.cookbook.life.controller.member

import com.cookbook.life.model.member.User
import com.cookbook.life.service.member.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(private val userService: UserService) {
    @PostMapping("/member/signup")
    fun signUp(@RequestBody requestData: User): User {
        return userService.createUser(requestData)
    }

    @GetMapping("/member/{id}")
    fun getUserById(@PathVariable id: Long): User {
        return userService.getUserById(id)
    }
}