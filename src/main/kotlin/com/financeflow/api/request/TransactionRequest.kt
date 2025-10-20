package com.financeflow.api.request

import java.math.BigDecimal

data class TransactionRequest(
    val type: String,
    val category: String,
    val amount: BigDecimal
)
