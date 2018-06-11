package br.com.alissonfpmorais.tiralama.auth.login.internal

sealed class LoginEvent

data class UsernameInputChanged(val username: String, val errorMsg: String) : LoginEvent()
data class PasswordInputChanged(val password: String, val errorMsg: String) : LoginEvent()
object LoginButtonClicked : LoginEvent()
object RegisterButtonClicked : LoginEvent()
object LoginSuccessful : LoginEvent()
data class LoginFailed(val errorMsg: String) : LoginEvent()
object NavigatedToMainActivity : LoginEvent()
object NavigatedToRegisterScreen : LoginEvent()
object ShowedLoginFailed : LoginEvent()