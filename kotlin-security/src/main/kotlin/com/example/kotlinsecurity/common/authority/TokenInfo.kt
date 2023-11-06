package com.example.kotlinsecurity.common.authority

data class TokenInfo(
    val grantType: String,
    val accessToken: String
)