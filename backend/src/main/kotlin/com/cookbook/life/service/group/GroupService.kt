package com.cookbook.life.service.group

import com.cookbook.life.model.group.Group
import com.cookbook.life.model.member.Member
import java.util.*

interface GroupService {
    fun getGroups(): List<Group>
    fun getGroup(groupId: UUID): Group
    fun getUsersByGroup(groupId: UUID): List<Member>
    fun createGroup(groupName: String, limitPerson: Int, groupPassword: String?): Group
    fun updateGroup(groupId: UUID, groupName: String?, limitPerson: Int?, groupPassword: String?): Group
    fun joinGroup(groupId: UUID, userId: UUID): String
    fun leaveGroup(groupId: UUID, userId: UUID): String
}