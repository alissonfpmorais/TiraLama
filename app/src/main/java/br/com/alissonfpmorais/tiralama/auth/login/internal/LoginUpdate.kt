package br.com.alissonfpmorais.tiralama.auth.login.internal

import android.util.Log
import br.com.alissonfpmorais.tiralama.common.validatePassword
import br.com.alissonfpmorais.tiralama.common.validateUsername
import com.spotify.mobius.Effects
import com.spotify.mobius.Next

typealias NextLogin = Next<LoginModel, LoginEffect>

fun loginUpdate(model: LoginModel, event: LoginEvent): NextLogin {
    return when (event) {
        is UsernameInputChanged -> onUsernameInputChanged(model, event.username, event.errorMsg)
        is PasswordInputChanged -> onPasswordInputChanged(model, event.password, event.errorMsg)
        is LoginButtonClicked -> onLoginButtonClicked(model)
        is RegisterButtonClicked -> onRegisterButtonClicked()
        is LoginSuccessful -> onLoginSuccessful()
        is LoginFailed -> onLoginFailed(model, event.errorMsg)
        is NavigatedToMainActivity -> onNavigatedToMainScreen()
        is NavigatedToRegisterScreen -> onNavigatedToRegisterScreen()
        is ShowedLoginFailed -> onShowedLoginFailed()
    }
}

fun isAbleToLogin(username: String, isUsernameValid: Boolean, password: String, isPasswordValid: Boolean): Boolean {
    val isFieldsValid = isUsernameValid && isPasswordValid
    val isFieldsEmpty = username == "" && password == ""

    return isFieldsValid && !isFieldsEmpty
}

fun onUsernameInputChanged(model: LoginModel, username: String, errorMsg: String): NextLogin {
    val isUsernameValid = validateUsername(username)
    val canLogin = isAbleToLogin(username, isUsernameValid, model.password, model.isPasswordValid)

    return Next.next(model.copy(
            username = username,
            isUsernameValid = isUsernameValid,
            userErrMsg = errorMsg,
            canLogin = canLogin))
}

fun onPasswordInputChanged(model: LoginModel, password: String, errorMsg: String): NextLogin {
    val isPasswordValid = validatePassword(password)
    val canLogin = isAbleToLogin(model.username, model.isUsernameValid, password, isPasswordValid)

    return Next.next(model.copy(
            password = password,
            isPasswordValid = isPasswordValid,
            passErrMsg = errorMsg,
            canLogin = canLogin))
}

fun onLoginButtonClicked(model: LoginModel): NextLogin {
    Log.d("Login", "onLoginButtonClicked")
    return if (model.canLogin && !model.isLogging) {
        Next.next(model.copy(canLogin = false, isLogging = true),
                Effects.effects(AttemptToLogin(model.username, model.password)))
    } else {
        Next.noChange()
    }
}

fun onRegisterButtonClicked(): NextLogin = Next.dispatch(Effects.effects(NavigateToRegisterScreen))

fun onLoginSuccessful(): NextLogin {
    Log.d("Login", "onLoginSuccessful")
    return Next.dispatch(Effects.effects(NavigateToMainScreen))
}

fun onLoginFailed(model: LoginModel, errorMsg: String): NextLogin {
    Log.d("Login", "onLoginFailed")
    return Next.next(model.copy(canLogin = true, isLogging = false),
            Effects.effects(ShowLoginFailed(errorMsg)))
}

fun onNavigatedToMainScreen(): NextLogin = Next.noChange()

fun onNavigatedToRegisterScreen(): NextLogin = Next.noChange()

fun onShowedLoginFailed(): NextLogin = Next.noChange()