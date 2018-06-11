package br.com.alissonfpmorais.tiralama.common.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import br.com.alissonfpmorais.tiralama.common.data.local.dao.CategoryDao
import br.com.alissonfpmorais.tiralama.common.data.local.dao.TransactionDao
import br.com.alissonfpmorais.tiralama.common.data.local.dao.UserDao
import br.com.alissonfpmorais.tiralama.common.data.local.entity.Category
import br.com.alissonfpmorais.tiralama.common.data.local.entity.Transaction
import br.com.alissonfpmorais.tiralama.common.data.local.entity.User

@Database(entities = [User::class, Transaction::class, Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        const val DATABASE_NAME = "tiralama-db"
    }
}