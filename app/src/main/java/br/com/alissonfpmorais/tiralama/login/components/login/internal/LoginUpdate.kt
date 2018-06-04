package br.com.alissonfpmorais.tiralama.login.components.login.internal

import com.spotify.mobius.Next

fun loginUpdate(model: LoginModel, event: LoginEvent): Next<LoginModel, LoginEffect> {
    return when (event) {
        is UserPreviousLogged -> onPreviousLogged()
        is UsernameInputChanged -> onUsernameInputChanged()
        is PasswordInputChanged -> onPasswordInputChanged()
        is LoginButtonClicked -> onLoginButtonClicked()
        is RegisterButtonClicked -> onRegisterButtonClicked()
        is LoginSuccessful -> onLoginSuccessful()
        is LoginFailed -> onLoginFailed()
    }
}

fun onPreviousLogged(): Next<LoginModel, LoginEffect> {
    TODO()
}

fun onUsernameInputChanged(): Next<LoginModel, LoginEffect> {
    TODO()
}

fun onPasswordInputChanged(): Next<LoginModel, LoginEffect> {
    TODO()
}

fun onLoginButtonClicked(): Next<LoginModel, LoginEffect> {
    TODO()
}

fun onRegisterButtonClicked(): Next<LoginModel, LoginEffect> {
    TODO()
}

fun onLoginSuccessful(): Next<LoginModel, LoginEffect> {
    TODO()
}

fun onLoginFailed(): Next<LoginModel, LoginEffect> {
    TODO()
}