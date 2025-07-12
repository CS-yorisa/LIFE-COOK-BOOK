package com.cookbook.life.controller.group

import com.cookbook.life.model.group.Group
import com.cookbook.life.model.member.Member
import com.cookbook.life.service.group.GroupService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class GroupController(
    private val groupService: GroupService
) {
    @QueryMapping
    fun getGroups(): List<Group> = groupService.getGroups()

    @QueryMapping
    fun getGroup(@Argument groupId: UUID): Group {
        return groupService.getGroup(groupId)
    }

    @QueryMapping
    fun getUsersByGroup(@Argument groupId: UUID): List<Member> =
        groupService.getUsersByGroup(groupId)

    @MutationMapping
    fun createGroup(
        @Argument groupName: String,
        @Argument limitPerson: Int,
        @Argument groupPassword: String?
    ): Group {
        return groupService.createGroup(groupName, limitPerson, groupPassword)
    }

    @MutationMapping
    fun updateGroup(
        @Argument groupId: UUID,
        @Argument groupName: String?,
        @Argument limitPerson: Int?,
        @Argument groupPassword: String?
    ): Group {
        return groupService.updateGroup(groupId, groupName, limitPerson, groupPassword)
    }

    @MutationMapping
    fun joinGroup(@Argument groupId: UUID, @Argument memberId: UUID): String {
        return groupService.joinGroup(groupId, memberId)
    }

    @MutationMapping
    fun leaveGroup(@Argument groupId: UUID, @Argument memberId: UUID): String {
        return groupService.leaveGroup(groupId, memberId)
    }
}