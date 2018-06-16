package br.com.alissonfpmorais.tiralama.common.data.source

import br.com.alissonfpmorais.tiralama.common.data.local.entity.User

interface UserSource {
    fun insertUser(user: User): Long
    fun listByLogged(isLogged: Boolean): List<User>
    fun listByUsernameAndPassword(username: String, password: String): List<User>
    fun update(user: User): Int
    fun truncate()
}