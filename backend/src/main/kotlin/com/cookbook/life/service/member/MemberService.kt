package com.cookbook.life.service.member

import com.cookbook.life.dto.member.SignUpDto
import com.cookbook.life.dto.member.SingInDTO
import com.cookbook.life.dto.member.Token
import com.cookbook.life.model.member.Member
import com.cookbook.life.repository.member.MemberRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwt: JWT,
) : UserDetailsService {

    fun singUp(singUpDtd: SignUpDto): Member {
        if (memberRepository.existsByEmail(singUpDtd.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        return createMember(
            Member(
                nickname = singUpDtd.nickname,
                email = singUpDtd.email,
                passWord = passwordEncoder.encode(singUpDtd.password),
            )
        )
    }

    fun singIn(singInDTO: SingInDTO): Token {
        val member = getUserByEmail(singInDTO.email)
            .takeIf { passwordEncoder.matches(singInDTO.passWord, it.password) }
            ?: throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.")

        val token = jwt.createTokenData(member)
        return token
    }

    fun authMember(email: String, password: String): Token {
        return singIn(SingInDTO(email, password))
    }

    fun verifyToken(token: String): Boolean {
        return jwt.isExpired(token)
    }

    override fun loadUserByUsername(email: String) =
        memberRepository.findByEmail(email) ?: throw EntityNotFoundException("Username: $email not found.")


    fun createMember(member: Member): Member {
        return memberRepository.save(member)
    }

    fun getUserByEmail(email: String): Member {
        return memberRepository.findByEmail(email) ?: throw IllegalArgumentException("User not found")
    }

    @Transactional
    fun addFriend(userId: UUID, friendId: UUID) {
//        TODO: 친구 추가 로직
        val user = memberRepository.findById(userId)
        val friend = memberRepository.findById(friendId)


//        user.friends.add(friend)
//        friend.friends.add(user)

//        userRepository.save(user)
//        userRepository.save(friend)
    }
}