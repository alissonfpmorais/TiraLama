package br.com.alissonfpmorais.tiralama.login.components.login.internal

sealed class LoginEffect

data class AttemptToLogin(val username: String, val password: String) : LoginEffect()
object NavigateToMainScreen : LoginEffect()
object NavigateToRegisterScreen : LoginEffect()