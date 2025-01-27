package com.cookbook.life.service.group

import com.cookbook.life.model.group.Group
import com.cookbook.life.model.group.GroupMapping
import com.cookbook.life.model.member.User
import com.cookbook.life.repository.group.GroupMappingRepository
import com.cookbook.life.repository.group.GroupRepository
import com.cookbook.life.repository.member.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class GroupServiceImpl (
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val groupMappingRepository: GroupMappingRepository
) : GroupService {
    override fun getGroups(): List<Group> = groupRepository.findAll()

    override fun getGroup(groupId: UUID): Group {
        return groupRepository.findById(groupId)
            .orElseThrow { throw IllegalArgumentException("Group not found with id: $groupId") }
    }

    override fun getUsersByGroup(groupId: UUID): List<User> =
        groupMappingRepository.findByGroup_GroupId(groupId).map { it.user }

    override fun createGroup(groupName: String, limitPerson: Int, groupPassword: String?): Group {
        return groupRepository.save(Group(groupName = groupName, limitPerson = limitPerson, groupPassword = groupPassword))
    }

    override fun updateGroup(groupId: UUID, groupName: String?, limitPerson: Int?, groupPassword: String?): Group {
        val group = groupRepository.findById(groupId).orElseThrow { IllegalArgumentException("Group not found") }
        return groupRepository.save(group.copy(
            groupName = groupName ?: group.groupName,
            limitPerson = limitPerson ?: group.limitPerson,
            groupPassword = groupPassword ?: group.groupPassword
        ))
    }

    override fun joinGroup(groupId: UUID, userId: UUID): String {
        val group = groupRepository.findById(groupId).orElseThrow { IllegalArgumentException("Group not found") }
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }
        groupMappingRepository.save(GroupMapping(group = group, user = user))
        return "User joined the group successfully"
    }

    override fun leaveGroup(groupId: UUID, userId: UUID): String {
        groupMappingRepository.deleteByGroup_GroupIdAndUser_Id(groupId, userId)
        return "User left the group successfully"
    }
}