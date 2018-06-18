package br.com.alissonfpmorais.tiralama.main.listtransaction.internal

import br.com.alissonfpmorais.tiralama.common.data.local.entity.Transaction

data class TransactionsModel(
        val transactions: List<Transaction> = listOf(),
        val totalBalance: Float = 0.0F,
        val positiveBalance: Float = 0.0F,
        val negativeBalance: Float = 0.0F)