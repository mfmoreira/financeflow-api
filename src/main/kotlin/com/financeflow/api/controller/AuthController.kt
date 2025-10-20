package com.financeflow.api.controller

import com.financeflow.api.request.LoginRequest
import com.financeflow.api.request.RegisterRequest
import com.financeflow.api.response.AuthResponse
import com.financeflow.api.response.UserResponse
import com.financeflow.api.security.JwtUtil
import com.financeflow.api.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder
) {
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<UserResponse> {
        val user = userService.register(request.name, request.email, request.password)
        val response = UserResponse(
            id = user.id,
            name = user.name,
            email = user.email
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthResponse> {
        val user = userService.findByEmail(request.email)
            ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        if (!passwordEncoder.matches(request.password, user.password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        val token = jwtUtil.generateToken(user.email)
        return ResponseEntity.ok(AuthResponse(token))
    }
}