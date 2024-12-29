package com.cookbook.life.model.member

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.NonNull
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

//import org.springframework.security.core.GrantedAuthority
//import java.security.Permission

@Entity(name = "users")
@Table(name = "users")
data class User(
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
    @Column(unique = true, nullable = false)
    val email: String,
    @JsonIgnore
    val passWord: String,

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    val roles: List<Role> = emptyList(),

    @Enumerated(EnumType.STRING)
    val type: MemberType = MemberType.USER

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
