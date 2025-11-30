package com.cookbook.life.model.member

import com.cookbook.life.model.group.GroupMapping
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "member")
data class Member(

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "member_friends",
        joinColumns = [JoinColumn(name = "member_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "friend_id", referencedColumnName = "id")]
    )
    val friends: MutableSet<Member> = mutableSetOf(),

    @Column(nullable = false, length = 30)
    val nickname: String,

    @Column(nullable = false, length = 30)
    val email: String,

    @Column(length = 100)
    val passWord: String,

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    val roles: List<Role> = emptyList(),

    @Enumerated(EnumType.STRING)
    val type: MemberType = MemberType.USER,

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    val groupMappings: MutableList<GroupMapping> = mutableListOf()

) : UserDetails {
    @JsonIgnore
    override fun getAuthorities() = this.roles

    @JsonIgnore
    override fun getPassword() = this.passWord

    @JsonIgnore
    override fun getUsername() = this.email

    @JsonIgnore
    override fun isAccountNonExpired() = true

    @JsonIgnore
    override fun isAccountNonLocked() = true

    @JsonIgnore
    override fun isCredentialsNonExpired() = true

    @JsonIgnore
    override fun isEnabled() = true
}