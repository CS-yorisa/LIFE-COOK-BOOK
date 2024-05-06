package com.cookbook.life.repository.member

import com.cookbook.life.model.member.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

//class memberRepository {
//}
@Repository
interface UserRepository: JpaRepository<User, Long> {
}