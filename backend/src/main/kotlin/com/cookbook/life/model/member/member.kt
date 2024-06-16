package com.cookbook.life.model.member

import jakarta.persistence.*
import lombok.NonNull
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.UUID
import org.springframework.security.core.userdetails.UserDetails
import com.fasterxml.jackson.annotation.JsonIgnore
//import org.springframework.security.core.GrantedAuthority
//import java.security.Permission

@Entity(name="users")
@Table(name="users")
data class User (
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),
//    val id: Long? = null,
//    val name: String,
    @Column(unique = true, nullable = false)
    val email: String,
    @JsonIgnore
    val passWord: String,

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    val roles: List<Role> = emptyList()

//    @Column(nullable = false)
//    val name: String,
//    @Column(unique = true, nullable = false)
//    val email: String,
//    @Column(name="password", nullable = false)
//    val password: String,
//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "users_pemissions",
//        joinColumns = [ JoinColumn(name = "user_id")],
//        inverseJoinColumns = [ JoinColumn(name = "permission_id") ]
//    )
//    val permissions: Set<Permission> = mutableSetOf()
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

//    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
//        TODO("Not yet implemented")
//    }
//
//    override fun getPassword(): String? {
//        TODO("Not yet implemented")
//        return this.password
//    }
//    override fun getAuthorities() = this.permissions

//    override fun getPassword() = this.password

//    override fun getUsername() = this.email
//
//    override fun isAccountNonExpired() = true
//
//    override fun isAccountNonLocked() = true
//
//    override fun isCredentialsNonExpired() = true
//
//    override fun isEnabled() = true
//    companion object {
//        fun from(request: SignUpRequest, encoder: PasswordEncoder) = Member(	// 파라미터에 PasswordEncoder 추가
//            account = request.account,
//            password = encoder.encode(request.password),	// 수정
//            name = request.name,
//            age = request.age
//        )
//    }
//
//    fun update(newMember: MemberUpdateRequest, encoder: PasswordEncoder) {	// 파라미터에 PasswordEncoder 추가
//        this.password = newMember.newPassword
//            ?.takeIf { it.isNotBlank() }
//            ?.let { encoder.encode(it) }	// 추가
//            ?: this.password
//        this.name = newMember.name
//        this.age = newMember.age
//    }
}
