package com.cookbook.life.repository.group

import com.cookbook.life.model.group.GroupMapping
import com.cookbook.life.model.group.GroupMappingId
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GroupMappingRepository : JpaRepository<GroupMapping, GroupMappingId> {
    fun findByGroup_GroupId(groupId: UUID): List<GroupMapping>
    fun deleteByGroup_GroupIdAndUser_Id(groupId: UUID, userId: UUID)
}