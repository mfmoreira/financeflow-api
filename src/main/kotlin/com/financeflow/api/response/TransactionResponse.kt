package com.financeflow.api.response

import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionResponse(
    val id: Long,
    val accountId: Long,
    val type: String,
    val category: String,
    val amount: BigDecimal,
    val createdAt: LocalDateTime
)
