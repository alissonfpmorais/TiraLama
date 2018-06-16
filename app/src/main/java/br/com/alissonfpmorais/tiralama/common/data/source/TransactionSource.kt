package br.com.alissonfpmorais.tiralama.common.data.source

import br.com.alissonfpmorais.tiralama.common.data.local.entity.Transaction
import io.reactivex.Flowable

interface TransactionSource {
    fun insert(transaction: Transaction): Long
    fun listTransactionsByUser(userId: String): Flowable<List<Transaction>>
    fun updateTransaction(transaction: Transaction): Int
    fun deleteTransaction(transaction: Transaction): Int
    fun truncate()
}