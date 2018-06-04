package br.com.alissonfpmorais.tiralama.login.components.login.internal

sealed class LoginEvent

object UserPreviousLogged : LoginEvent()
data class UsernameInputChanged(val username: String) : LoginEvent()
data class PasswordInputChanged(val password: String) : LoginEvent()
data class LoginButtonClicked(val username: String, val password: String) : LoginEvent()
object RegisterButtonClicked : LoginEvent()
object LoginSuccessful : LoginEvent()
data class LoginFailed(val errorMsg: String)