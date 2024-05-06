package com.cookbook.life.model.member

import jakarta.persistence.*
import lombok.NonNull
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.UUID

@Entity(name="users")
@Table(name="users")
data class User(
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID = UUID.randomUUID(),
    @Column(nullable = false)
    var name: String,
    @Column(unique = true, nullable = false)
    var email: String,
    @Column(nullable = false)
    var password: String,
)
