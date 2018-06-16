package br.com.alissonfpmorais.tiralama.main.components.listtransaction.internal

sealed class TransactionsEffect

object FillTransactionsList : TransactionsEffect()
object NavigateToAddTransactionScreen : TransactionsEffect()