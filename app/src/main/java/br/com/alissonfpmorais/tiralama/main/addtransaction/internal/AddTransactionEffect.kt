package br.com.alissonfpmorais.tiralama.main.addtransaction.internal

sealed class AddTransactionEffect

data class AttemptToSave(val name: String, val value: String) : AddTransactionEffect()
object NavigateToTransactionsScreen : AddTransactionEffect()
data class ShowSaveFailed(val errorMsg: String) : AddTransactionEffect()