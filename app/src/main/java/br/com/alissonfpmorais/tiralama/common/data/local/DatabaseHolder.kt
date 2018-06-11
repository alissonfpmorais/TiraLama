package br.com.alissonfpmorais.tiralama.common.data.local

import android.arch.persistence.room.Room
import android.content.Context
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

object DatabaseHolder {
    private lateinit var database: AppDatabase

    /**
     * Get and return a database instance
     * @param [Context]
     * @return a [Single] of [AppDatabase]
     */
    fun getSingleInstance(context: Context): Single<AppDatabase> {
        return Single.just(this::database.isInitialized)
                .subscribeOn(Schedulers.computation())
                .map { isDatabaseCreated ->
                    if (!isDatabaseCreated) database =
                            Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME).build()
                    database
                }
    }
}