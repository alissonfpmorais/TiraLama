package br.com.alissonfpmorais.tiralama.main.listtransaction.internal

sealed class TransactionsEffect

object UpdateTransactionsList : TransactionsEffect()
object NavigateToAddTransactionScreen : TransactionsEffect()