package com.financeflow.api.controller

import com.financeflow.api.request.TransactionRequest
import com.financeflow.api.response.TransactionResponse
import com.financeflow.api.service.TransactionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/transactions")
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping
    fun create(@RequestBody request: TransactionRequest, principal: Principal): ResponseEntity<TransactionResponse> {
        val transaction = transactionService.create(
            userEmail = principal.name,
            type = request.type,
            category = request.category,
            amount = request.amount
        )

        val response = TransactionResponse(
            id = transaction.id,
            accountId = transaction.account.id,
            type = transaction.type,
            category = transaction.category,
            amount = transaction.amount,
            createdAt = transaction.createdAt
        )
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun list(principal: Principal): ResponseEntity<List<TransactionResponse>> {
        val transactions = transactionService.listTransactions(principal.name)

        return ResponseEntity.ok(
            transactions.map { t ->
                TransactionResponse(
                    id = t.id,
                    accountId = t.account.id,
                    type = t.type,
                    category = t.category,
                    amount = t.amount,
                    createdAt = t.createdAt
                )
            }
        )
    }
}
