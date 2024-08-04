package com.cookbook.life.security

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String,
)