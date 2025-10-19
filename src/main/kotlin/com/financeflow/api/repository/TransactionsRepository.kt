package com.financeflow.api.repository

import com.financeflow.api.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionsRepository : JpaRepository<Transaction, Long> {
    fun findByAccountId(accountId: Long): List<Transaction>
}