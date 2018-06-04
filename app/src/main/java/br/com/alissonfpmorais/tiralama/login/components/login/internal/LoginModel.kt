package br.com.alissonfpmorais.tiralama.login.components.login.internal

data class LoginModel(val username: String = "",
                      val password: String = "",
                      val isUsernameValid: Boolean = true,
                      val isPasswordValid: Boolean = true,
                      val usernameErrorMsg: String = "",
                      val passwordErrorMsg: String = "",
                      val isLogging: Boolean = false,
                      val canLogin: Boolean = false)