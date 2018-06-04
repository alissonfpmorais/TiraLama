package br.com.alissonfpmorais.tiralama.common.data.source

import br.com.alissonfpmorais.tiralama.common.data.local.entity.Transaction

interface TransactionSource {
    fun insertTransaction(transaction: Transaction)
    fun listTransactions(): List<Transaction>
    fun updateTransaction(transaction: Transaction)
    fun truncate()
}