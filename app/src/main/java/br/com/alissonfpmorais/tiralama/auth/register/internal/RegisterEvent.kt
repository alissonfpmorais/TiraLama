package br.com.alissonfpmorais.tiralama.auth.register.internal

sealed class RegisterEvent

data class UsernameInputChanged(val username: String, val errorMsg: String) : RegisterEvent()
data class PasswordInputChanged(val password: String,
                                val confirmation: String,
                                val passErrMsg: String,
                                val confirmErrMsg: String) : RegisterEvent()
object RegisterButtonClicked : RegisterEvent()
//object BackButtonClicked : RegisterEvent()
object RegisterSuccessful : RegisterEvent()
data class RegisterFailed(val errorMsg: String) : RegisterEvent()
object NavigatedToLoginScreen : RegisterEvent()
object ShowedRegisterFailed : RegisterEvent()