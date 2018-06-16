package br.com.alissonfpmorais.tiralama.main.components.addtransaction.internal

data class AddTransactionModel(
        val name: String = "",
        val value: String = "",
        val isNameValid: Boolean = true,
        val isValueValid: Boolean = true,
        val nameErrMsg: String = "",
        val valueErrMsg: String = "",
        val canSave: Boolean = false,
        val isSaving: Boolean = false)