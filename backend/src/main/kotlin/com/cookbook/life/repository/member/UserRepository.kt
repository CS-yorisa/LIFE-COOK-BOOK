package com.cookbook.life.repository.member

import com.cookbook.life.model.member.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID>