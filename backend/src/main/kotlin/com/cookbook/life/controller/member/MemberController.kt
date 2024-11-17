package com.cookbook.life.controller.member

import com.cookbook.life.app.utility.EndPoint
import com.cookbook.life.dto.member.SignUpDto
import com.cookbook.life.dto.member.SingInDTO
import com.cookbook.life.dto.member.Token
import com.cookbook.life.model.member.User
import com.cookbook.life.service.member.UserService
import org.springframework.http.ResponseEntity
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
//        println("MemberController.signUp")
//        return userService.createUser(singUpDto)
        try {
            val user = userService.singUp(singUpDto)
            return ResponseEntity.ok(user)
//            return userService.singUp(singUpDto)
        } catch (e: Exception) {
            return ResponseEntity.status(400).body(null)
        }
    }

//    @GetMapping("/{id}")
//    fun getUserById(@PathVariable id: UUID)  {
//        return
////        return userService.getUserById(id)
////        return userService.getUserById("zxcvzxcvzxcvzxcvxzcv")
//    }

    @PostMapping(EndPoint.SIGN_IN)
    fun singIn(@RequestBody singInDTO: SingInDTO): ResponseEntity<Token> {
        val token = userService.singIn(singInDTO)
        return ResponseEntity.ok(token)
    }

    @GetMapping(EndPoint.VERIFY_TOKEN)
    fun verifyToken(@RequestBody token: String): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userService.verifyToken(token))
    }
}