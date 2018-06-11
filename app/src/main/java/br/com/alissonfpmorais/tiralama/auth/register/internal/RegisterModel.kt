package br.com.alissonfpmorais.tiralama.auth.register.internal

data class RegisterModel(val username: String = "",
                         val password: String = "",
                         val confirmation: String = "",
                         val isUsernameValid: Boolean = true,
                         val isPasswordValid: Boolean = true,
                         val isConfirmationValid: Boolean = true,
                         val userErrMsg: String = "",
                         val passErrMsg: String = "",
                         val confirmErrMsg: String = "",
                         val canRegister: Boolean = false,
                         val isRegistering: Boolean = false)