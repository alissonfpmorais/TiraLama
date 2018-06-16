package br.com.alissonfpmorais.tiralama.common.data.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(
        tableName = "user",
        indices = [(Index(value = ["username"]))]
)
data class User(
        @PrimaryKey val username: String,
        val password: String,
        val isLogged: Boolean)