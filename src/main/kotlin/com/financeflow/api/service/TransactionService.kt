package com.financeflow.api.service

import com.financeflow.api.model.Account
import com.financeflow.api.model.Transaction
import com.financeflow.api.model.User
import com.financeflow.api.repository.AccountRepository
import com.financeflow.api.repository.TransactionsRepository
import com.financeflow.api.repository.UserRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransactionService(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
    private val transactionsRepository: TransactionsRepository
) {

    fun create(userEmail: String, type: String, category: String, amount: BigDecimal): Transaction {
        val user: User = userRepository.findByEmail(userEmail)
            ?: throw IllegalArgumentException("User not found with email $userEmail")

        val account: Account = accountRepository.findByUserId(user.id).firstOrNull() ?: run {
            val newAccount = Account(
                name = "Default Account",
                balance = BigDecimal.ZERO,
                user = user
            )
            accountRepository.save(newAccount)
        }

        if (type == "EXPENSE" && account.balance < amount) {
            throw IllegalArgumentException("Insufficient funds for account id ${account.id}")
        }

        val transaction = Transaction(
            account = account,
            type = type,
            category = category,
            amount = amount
        )
        val savedTransaction = transactionsRepository.save(transaction)

        account.balance = if (type == "INCOME") {
            account.balance + amount
        } else {
            account.balance - amount
        }
        accountRepository.save(account)

        return savedTransaction
    }

    fun listTransactions(userEmail: String): List<Transaction> {
        val user: User = userRepository.findByEmail(userEmail)
            ?: throw IllegalArgumentException("User not found with email $userEmail")

        val account: Account = accountRepository.findByUserId(user.id).firstOrNull()
            ?: throw IllegalArgumentException("No account found for user id ${user.id}")

        return transactionsRepository.findByAccountId(account.id)
    }
}
