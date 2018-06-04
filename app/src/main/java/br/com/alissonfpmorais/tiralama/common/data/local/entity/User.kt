package br.com.alissonfpmorais.tiralama.common.data.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: String,
    val username: String,
    val password: String,
    val isLogged: Boolean)