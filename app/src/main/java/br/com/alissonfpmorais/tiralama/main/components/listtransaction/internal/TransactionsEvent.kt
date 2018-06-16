package br.com.alissonfpmorais.tiralama.main.components.listtransaction.internal

import br.com.alissonfpmorais.tiralama.common.data.local.entity.Transaction

sealed class TransactionsEvent

object RequestUpdatableTransactionsList : TransactionsEvent()
object FloatingButtonClicked : TransactionsEvent()
object NavigatedToAddTransactionScreen : TransactionsEvent()
data class NewTransactionsArrive(val transactions: List<Transaction>) : TransactionsEvent()