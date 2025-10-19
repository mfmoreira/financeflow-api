package com.financeflow.api.service

import com.financeflow.api.model.User
import com.financeflow.api.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun register(name: String, email: String, password: String): User {
        userRepository.findByEmail(email)?.let {
            throw IllegalArgumentException("Email already in use")
        }

            val encodedPassword = passwordEncoder.encode(password)
            return userRepository.save(User(name = name, email = email, password = encodedPassword))
        }

        fun findByEmail(email: String): User? =
             userRepository.findByEmail(email)

}