package com.cookbook.life.model.group

import com.cookbook.life.model.member.Member
import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "group_mapping")
@IdClass(GroupMappingId::class)
data class GroupMapping(

    @Id @ManyToOne @JoinColumn(name = "groupId")
    val group: Group,

    @Id @ManyToOne @JoinColumn(name = "memberId")
    val member: Member
)

data class GroupMappingId(
    val group: UUID = UUID.randomUUID(),
    val member: UUID = UUID.randomUUID()
) : Serializable