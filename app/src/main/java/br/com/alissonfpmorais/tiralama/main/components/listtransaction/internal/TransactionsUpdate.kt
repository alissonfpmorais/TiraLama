package br.com.alissonfpmorais.tiralama.main.components.listtransaction.internal

import br.com.alissonfpmorais.tiralama.common.data.local.entity.Transaction
import com.spotify.mobius.Effects
import com.spotify.mobius.Next

typealias NextTransactions = Next<TransactionsModel, TransactionsEffect>

@Suppress("UNUSED_PARAMETER")
fun transactionsUpdate(model: TransactionsModel, event: TransactionsEvent): NextTransactions {
    return when (event) {
        is RequestUpdatableTransactionsList -> onRequestUpdatableTransactionsList()
        is FloatingButtonClicked -> onFloatingButtonClicked()
        is NavigatedToAddTransactionScreen -> onNavigatedToAddTransactionScreen()
        is NewTransactionsArrive -> onNewTransactionsArrive(model, event.transactions)
    }
}

fun onRequestUpdatableTransactionsList(): NextTransactions = Next.dispatch(Effects.effects(FillTransactionsList))

fun onFloatingButtonClicked(): NextTransactions = Next.dispatch(Effects.effects(NavigateToAddTransactionScreen))

fun onNavigatedToAddTransactionScreen(): NextTransactions = Next.noChange()

fun onNewTransactionsArrive(model: TransactionsModel, transactions: List<Transaction>): NextTransactions = Next.next(model.copy(transactions = transactions))