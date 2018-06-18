package br.com.alissonfpmorais.tiralama.main.listtransaction.internal

import br.com.alissonfpmorais.tiralama.common.data.local.entity.Transaction

sealed class TransactionsEvent

object RequestUpdatableTransactionsList : TransactionsEvent()
object FloatingButtonClicked : TransactionsEvent()
object NavigatedToAddTransactionScreen : TransactionsEvent()
data class TransactionsListUpdated(val transactions: List<Transaction>) : TransactionsEvent()