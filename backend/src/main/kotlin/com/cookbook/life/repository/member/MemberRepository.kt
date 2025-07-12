package com.cookbook.life.repository.member

import com.cookbook.life.model.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository : JpaRepository<Member, UUID> {
    fun findByEmail(email: String): Member?
    fun existsByEmail(email: String): Boolean
}