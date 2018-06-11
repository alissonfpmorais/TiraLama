package br.com.alissonfpmorais.tiralama.auth.register.internal

sealed class RegisterEffect

data class AttemptToRegister(val username: String, val password: String) : RegisterEffect()
object NavigateToLoginScreen : RegisterEffect()
data class ShowRegisterFailed(val errorMsg: String) : RegisterEffect()