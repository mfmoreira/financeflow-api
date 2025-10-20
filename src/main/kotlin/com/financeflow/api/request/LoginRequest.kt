package com.financeflow.api.request

data class LoginRequest(
    val email: String,
    val password: String
)