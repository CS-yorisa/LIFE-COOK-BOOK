package com.cookbook.life.model.group

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "groups")
data class Group (

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val groupId: UUID = UUID.randomUUID(),

    @Column(nullable = false, length = 200)
    val groupName: String,

    @Column(nullable = false)
    val limitPerson: Int,

    @Column(length = 4)
    val groupPassword: String?,

    @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL], orphanRemoval = true)
    val groupMappings: MutableList<GroupMapping> = mutableListOf()
)