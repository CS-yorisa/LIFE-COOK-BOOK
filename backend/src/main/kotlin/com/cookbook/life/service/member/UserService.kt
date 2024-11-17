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
//            passWord = singUpDtd.password,
                passWord = passwordEncoder.encode(singUpDtd.password),
            )
        )
    }

//    @Autowired
//    lateinit var authenticationManager: AuthenticationManager

    fun singIn(singInDTO: SingInDTO): Token {
//        println(singInDTO)
//        val user = loadUserByUsername(singInDTO.email)
        val user = getUserByEmail(singInDTO.email)
//        println(1111111111111)
//        println(user)
//        println(passwordEncoder.matches(singInDTO.passWord, user.passWord))
        if (!passwordEncoder.matches(singInDTO.passWord, user.passWord)) {
            throw IllegalArgumentException("Password is incorrect")
        }
//        println(2222222222222)

//        val authenticationToken = UsernamePasswordAuthenticationToken(singInDTO.email, singInDTO.passWord)
//        println(authenticationToken)
//        println(3333333333333)
//        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
//        println(authentication)
//        println(4444444444444)

//        val authentication =
//            authenticationManager.authenticate(
//                UsernamePasswordAuthenticationToken(
//                    singInDTO.email,
//                    singInDTO.passWord
//                )
//            )
//
        val token = jwt.createTokenData(user)
//        println(token)
        return token

//        val expirationDate: Date = Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)
//        val access = jwt.createToken(email=user.email, expirationDate = expirationDate)
//        print(access)
        // 우선 같은 토큰으로 access, refresh 둘 다 반환
//        return Token(access,access)
    }

    fun verifyToken(token: String): Boolean {
        println(9999999999)
        println(token)
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