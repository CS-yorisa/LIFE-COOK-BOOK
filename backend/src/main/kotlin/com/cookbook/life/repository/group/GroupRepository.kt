package com.cookbook.life.repository.group

import com.cookbook.life.model.group.Group
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GroupRepository : JpaRepository<Group, UUID>