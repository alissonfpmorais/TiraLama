package br.com.alissonfpmorais.tiralama.main.components.addtransaction.internal

sealed class AddTransactionEvent

data class TransactionNameInputChanged(val name: String, val errorMsg: String) : AddTransactionEvent()
data class TransactionValueInputChanged(val value: String, val errorMsg: String) : AddTransactionEvent()
object SaveButtonClicked : AddTransactionEvent()
object SaveSuccessful : AddTransactionEvent()
data class SaveFailed(val errorMsg: String) : AddTransactionEvent()
object NavigatedToTransactionsScreen : AddTransactionEvent()
object ShowedSaveFailed : AddTransactionEvent()