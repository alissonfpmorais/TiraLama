package br.com.alissonfpmorais.tiralama.auth.login.internal

data class LoginModel(val username: String = "",
                      val password: String = "",
                      val isUsernameValid: Boolean = true,
                      val isPasswordValid: Boolean = true,
                      val userErrMsg: String = "",
                      val passErrMsg: String = "",
                      val canLogin: Boolean = false,
                      val isLogging: Boolean = false)