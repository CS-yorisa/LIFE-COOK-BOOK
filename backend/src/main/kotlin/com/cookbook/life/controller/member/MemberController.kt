package com.cookbook.life.controller.member

import com.cookbook.life.app.utility.EndPoint
import com.cookbook.life.dto.member.SignUpDto
import com.cookbook.life.dto.member.SingInDTO
import com.cookbook.life.dto.member.Token
import com.cookbook.life.model.member.Member
import com.cookbook.life.service.member.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(EndPoint.AUTH_ROOT_PATH)
class MemberController(private val memberService: MemberService) {
    @GetMapping("")
    fun apiTest(): String {
        return "test"
    }

//    @MutationMapping
//    fun createMember(@Argument signUpDto: SignUpDto): Member {
//        return memberService.singUp(signUpDto)
//    }
//
//    @MutationMapping
//    fun authMember(
//        @Argument email: String,
//        @Argument password: String
//    ): Token {
//        println(email)
//        println(password)
////        return userService.singIn(singInDTO)
//        return memberService.authMember(email, password)
//    }

    @PostMapping(EndPoint.SIGN_UP)
    fun signUp(@RequestBody singUpDto: SignUpDto): ResponseEntity<Member> {
        try {
            val member = memberService.singUp(singUpDto)
            return ResponseEntity.ok(member)
        } catch (e: Exception) {
            return ResponseEntity.status(400).body(null)
        }
    }

    @PostMapping(EndPoint.SIGN_IN)
    fun singIn(@RequestBody singInDTO: SingInDTO): ResponseEntity<Token> {
        val token = memberService.singIn(singInDTO)
        return ResponseEntity.ok(token)
    }

    @GetMapping(EndPoint.VERIFY_TOKEN)
    fun verifyToken(@RequestBody token: String): ResponseEntity<Boolean> {
        return ResponseEntity.ok(memberService.verifyToken(token))
    }

    @GetMapping("/test")
    fun testAPI(@AuthenticationPrincipal member: org.springframework.security.core.userdetails.User): String {
        println(member)
        return "test"
    }
}