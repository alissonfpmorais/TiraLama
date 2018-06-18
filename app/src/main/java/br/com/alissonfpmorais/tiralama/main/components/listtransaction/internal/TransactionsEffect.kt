package br.com.alissonfpmorais.tiralama.main.components.listtransaction.internal

sealed class TransactionsEffect

object UpdateTransactionsList : TransactionsEffect()
object NavigateToAddTransactionScreen : TransactionsEffect()