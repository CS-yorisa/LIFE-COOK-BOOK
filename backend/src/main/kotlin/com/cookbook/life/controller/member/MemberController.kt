package com.cookbook.life.controller.member

import com.cookbook.life.app.utility.EndPoint
import com.cookbook.life.dto.member.SignUpDto
import com.cookbook.life.dto.member.SingInDTO
import com.cookbook.life.dto.member.Token
import com.cookbook.life.model.member.User
import com.cookbook.life.service.member.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(EndPoint.AUTH_ROOT_PATH)
class MemberController(private val userService: UserService) {
    @GetMapping("")
    fun apiTest(): String {
        return "test"
    }

    @PostMapping(EndPoint.SIGN_UP)
    fun signUp(@RequestBody singUpDto: SignUpDto): ResponseEntity<User> {
        try {
            val user = userService.singUp(singUpDto)
            return ResponseEntity.ok(user)
        } catch (e: Exception) {
            return ResponseEntity.status(400).body(null)
        }
    }

    @PostMapping(EndPoint.SIGN_IN)
    fun singIn(@RequestBody singInDTO: SingInDTO): ResponseEntity<Token> {
        val token = userService.singIn(singInDTO)
        return ResponseEntity.ok(token)
    }

    @GetMapping(EndPoint.VERIFY_TOKEN)
    fun verifyToken(@RequestBody token: String): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userService.verifyToken(token))
    }

    @GetMapping("/test")
    fun testAPI(@AuthenticationPrincipal member: org.springframework.security.core.userdetails.User): String {
        println(member)
        return "test"
    }
}