package br.com.alissonfpmorais.tiralama.auth.register.internal

import br.com.alissonfpmorais.tiralama.common.validateConfirmation
import br.com.alissonfpmorais.tiralama.common.validatePassword
import br.com.alissonfpmorais.tiralama.common.validateUsername
import com.spotify.mobius.Effects
import com.spotify.mobius.Next

typealias NextRegister = Next<RegisterModel, RegisterEffect>

fun registerUpdate(model: RegisterModel, event: RegisterEvent): NextRegister {
    return when (event) {
        is UsernameInputChanged -> onUsernameInputChanged(model, event.username, event.errorMsg)
        is PasswordInputChanged -> onPasswordInputChanged(model, event.password, event.confirmation, event.passErrMsg, event.confirmErrMsg)
        is RegisterButtonClicked -> onRegisterButtonClicked(model)
//        is BackButtonClicked -> onBackButtonClicked()
        is RegisterSuccessful -> onRegisterSuccessful()
        is RegisterFailed -> onRegisterFailed(model, event.errorMsg)
        is NavigatedToLoginScreen -> onNavigatedToLoginScreen()
        is ShowedRegisterFailed -> onShowedRegisterFailed()
    }
}

fun isAbleToRegister(
        username: String,
        isUsernameValid: Boolean,
        password: String,
        isPasswordValid: Boolean,
        confirmation: String,
        isConfirmationValid: Boolean): Boolean {
    val isFieldsValid = isUsernameValid && isPasswordValid && isConfirmationValid
    val isFieldsEmpty = username == "" && password == "" && confirmation == ""

    return isFieldsValid && !isFieldsEmpty
}

fun onUsernameInputChanged(model: RegisterModel, username: String, errorMsg: String): NextRegister {
    val isUsernameValid = validateUsername(username)
    val canRegister = isAbleToRegister(username, isUsernameValid, model.password, model.isPasswordValid, model.confirmation, model.isConfirmationValid)

    return Next.next(model.copy(
            username = username,
            isUsernameValid = isUsernameValid,
            userErrMsg = errorMsg,
            canRegister = canRegister))
}

fun onPasswordInputChanged(model: RegisterModel, password: String, confirmation: String, passErrMsg: String, confirmErrMsg: String): NextRegister {
    val isPasswordValid = validatePassword(password)
    val isConfirmationValid = validateConfirmation(password, confirmation)
    val canRegister = isAbleToRegister(model.username, model.isUsernameValid, password, isPasswordValid, confirmation, isConfirmationValid)

    return Next.next(model.copy(
            password = password,
            confirmation = confirmation,
            isPasswordValid = isPasswordValid,
            isConfirmationValid = isConfirmationValid,
            passErrMsg = passErrMsg,
            confirmErrMsg = confirmErrMsg,
            canRegister = canRegister))
}

fun onRegisterButtonClicked(model: RegisterModel): NextRegister {
    return if (model.canRegister) {
        Next.next(model.copy(canRegister = false, isRegistering = true),
                Effects.effects(AttemptToRegister(model.username, model.password)))
    } else {
        Next.noChange()
    }
}

//fun onBackButtonClicked(): NextRegister = Next.next(RegisterModel(), Effects.effects(NavigateToLoginScreen))

fun onRegisterSuccessful(): NextRegister = Next.next(RegisterModel(), Effects.effects(NavigateToLoginScreen))

fun onRegisterFailed(model: RegisterModel, errorMsg: String): NextRegister {
    return Next.next(model.copy(canRegister = true, isRegistering = false),
            Effects.effects(ShowRegisterFailed(errorMsg)))
}

fun onNavigatedToLoginScreen(): NextRegister = Next.noChange()

fun onShowedRegisterFailed(): NextRegister = Next.noChange()