package com.cookbook.life.model.member

import com.cookbook.life.model.group.GroupMapping
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
data class User (

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, length = 30)
    val nickname: String,

    @Column(nullable = false, length = 30)
    val email: String,

    @Column(length = 30)
    val password: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val groupMappings: MutableList<GroupMapping> = mutableListOf()
)