package br.com.alissonfpmorais.tiralama.common.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import br.com.alissonfpmorais.tiralama.common.data.local.entity.Transaction
import br.com.alissonfpmorais.tiralama.common.data.source.TransactionSource

@Dao
interface TransactionDao : TransactionSource {
    @Insert
    override fun insertTransaction(transaction: Transaction): Long

    @Query("select * from `transaction`")
    override fun listTransactions(): List<Transaction>

    @Update
    override fun updateTransaction(transaction: Transaction): Int

    @Query("delete from `transaction`")
    override fun truncate()
}