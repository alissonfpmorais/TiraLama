package br.com.alissonfpmorais.tiralama.auth.login.internal

sealed class LoginEffect

data class AttemptToLogin(val username: String, val password: String) : LoginEffect()
data class ShowLoginFailed(val errorMsg: String) : LoginEffect()
object NavigateToMainScreen : LoginEffect()
object NavigateToRegisterScreen : LoginEffect()