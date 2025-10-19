package com.financeflow.api.service

import com.financeflow.api.model.Transaction
import com.financeflow.api.repository.AccountRepository
import com.financeflow.api.repository.TransactionsRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransactionService(
    private val accountRepository: AccountRepository,
    private val transactionsRepository: TransactionsRepository
) {
    fun create(accountId: Long, type: String, category: String, amount: BigDecimal): Transaction {
        val account = accountRepository.findById(accountId).orElseThrow()
        if(type == "EXPENSE" && account.balance < amount) {
            throw IllegalArgumentException("Insufficient funds")
        }

        val transaction = transactionsRepository.save(
            Transaction(
                account = account,
                type = type,
                category = category,
                amount = amount
            ))

        account.balance = if (type == "INCOME")
            account.balance + amount
        else
            account.balance - amount
            accountRepository.save(account)
        return transaction
    }

    fun listTransactions(accountId: Long): List<Transaction> =
         transactionsRepository.findByAccountId(accountId)

}