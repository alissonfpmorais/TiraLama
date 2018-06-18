package br.com.alissonfpmorais.tiralama.main.addtransaction.internal

import br.com.alissonfpmorais.tiralama.common.validateTransactionName
import br.com.alissonfpmorais.tiralama.common.validateTransactionValue
import com.spotify.mobius.Effects
import com.spotify.mobius.Next

typealias NextAddTransaction = Next<AddTransactionModel, AddTransactionEffect>

fun addTransactionUpdate(model: AddTransactionModel, event: AddTransactionEvent): NextAddTransaction {
    return when (event) {
        is TransactionNameInputChanged -> onTransactionNameInputChanged(model, event.name, event.errorMsg)
        is TransactionValueInputChanged -> onTransactionValueInputChanged(model, event.value, event.errorMsg)
        is SaveButtonClicked -> onSaveButtonClicked(model)
        is SaveSuccessful -> onSaveSuccessful()
        is SaveFailed -> onSaveFailed(model, event.errorMsg)
        is NavigatedToTransactionsScreen -> onNavigatedToTransactionsScreen()
        is ShowedSaveFailed -> onShowedSaveFailed()
    }
}

fun isAbleToSave(name: String, value: String, isNameValid: Boolean, isValueValid: Boolean): Boolean {
    val isFieldsValid = isNameValid && isValueValid
    val isFieldsEmpty = name != "" && value != ""

    return isFieldsValid && isFieldsEmpty
}

fun onTransactionNameInputChanged(model: AddTransactionModel, name: String, errorMsg: String): NextAddTransaction {
    val isNameValid = validateTransactionName(name)
    val canSave = isAbleToSave(name, model.value, isNameValid, model.isValueValid)

    return Next.next(model.copy(
            name = name,
            isNameValid = isNameValid,
            nameErrMsg = errorMsg,
            canSave = canSave))
}

fun onTransactionValueInputChanged(model: AddTransactionModel, value: String, errorMsg: String): NextAddTransaction {
    val isValueValid = validateTransactionValue(value)
    val canSave = isAbleToSave(model.name, value, model.isNameValid, isValueValid)

    return Next.next(model.copy(
            value = value,
            isValueValid = isValueValid,
            valueErrMsg = errorMsg,
            canSave = canSave))
}

fun onSaveButtonClicked(model: AddTransactionModel): NextAddTransaction {
    return if (model.canSave && !model.isSaving) {
        Next.next(model.copy(canSave = false, isSaving = true),
                Effects.effects(AttemptToSave(model.name, model.value)))
    } else {
        Next.noChange()
    }
}

fun onSaveSuccessful(): NextAddTransaction = Next.next(AddTransactionModel(), Effects.effects(NavigateToTransactionsScreen))

fun onSaveFailed(model: AddTransactionModel, errorMsg: String): NextAddTransaction {
    return Next.next(model.copy(canSave = true, isSaving = false),
            Effects.effects(ShowSaveFailed(errorMsg)))
}

fun onNavigatedToTransactionsScreen(): NextAddTransaction = Next.noChange()

fun onShowedSaveFailed(): NextAddTransaction = Next.noChange()