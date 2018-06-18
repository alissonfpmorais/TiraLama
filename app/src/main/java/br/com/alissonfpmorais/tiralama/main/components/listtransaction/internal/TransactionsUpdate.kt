package br.com.alissonfpmorais.tiralama.main.components.listtransaction.internal

import android.util.Log
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
        is TransactionsListUpdated -> onTransactionsListUpdated(model, event.transactions)
    }
}

fun onRequestUpdatableTransactionsList(): NextTransactions = Next.dispatch(Effects.effects(UpdateTransactionsList))

fun onFloatingButtonClicked(): NextTransactions = Next.dispatch(Effects.effects(NavigateToAddTransactionScreen))

fun onNavigatedToAddTransactionScreen(): NextTransactions = Next.noChange()

fun onTransactionsListUpdated(model: TransactionsModel, transactions: List<Transaction>): NextTransactions {
    Log.d("truta", "dentro do update, $transactions")

    val inTransactions = transactions
            .map { transaction -> transaction.value}
            .filter { value -> value >= 0 }

    val outTransactions = transactions
            .map { transaction -> transaction.value}
            .filter { value -> value < 0 }


    val positiveBalance = when (inTransactions.isNotEmpty()) {
        true -> inTransactions.reduce { acc, value -> acc + value }
        false -> 0.0F
    }

    val negativeBalance = when (outTransactions.isNotEmpty()) {
        true -> outTransactions.reduce { acc, value -> acc + value }
        false -> 0.0F
    }

    val totalBalance = positiveBalance + negativeBalance

    Log.d("truta", "ainda tem transactions? $transactions")
    Log.d("truta", "total: $totalBalance, +: $positiveBalance, -: $negativeBalance")

    return Next.next(model.copy(
            transactions = transactions,
            totalBalance = totalBalance,
            positiveBalance = positiveBalance,
            negativeBalance = negativeBalance))
}