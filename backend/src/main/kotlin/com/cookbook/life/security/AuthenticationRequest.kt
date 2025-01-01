package com.cookbook.life.security

data class AuthenticationRequest(
    val email: String,
    val password: String,
)