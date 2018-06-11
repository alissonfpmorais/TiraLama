package br.com.alissonfpmorais.tiralama.common

fun validateUsername(username: String): Boolean = textGreaterThan(username, 2)

fun validatePassword(password: String): Boolean {
//    // At least 8 characters, including: uppercase, lowercase, digit and specials
//    val regex: Regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$".toRegex()

    // At least 4 characters, including: letters and digits
    val regex: Regex = "^(?=.*?[a-zA-Z])(?=.*?[0-9]).{4,}\$".toRegex()
    return password.matches(regex)
}

fun validateConfirmation(password: String, confirmation: String): Boolean {
    return validatePassword(confirmation) && password == confirmation
}

fun textGreaterThan(text: String, max: Int): Boolean = text.length > max