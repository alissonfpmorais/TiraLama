package br.com.alissonfpmorais.tiralama.common.data.local.dao

import android.arch.persistence.room.*
import br.com.alissonfpmorais.tiralama.common.data.local.entity.Transaction
import br.com.alissonfpmorais.tiralama.common.data.source.TransactionSource
import io.reactivex.Flowable

@Dao
interface TransactionDao : TransactionSource {
    @Insert
    override fun insert(transaction: Transaction): Long

    @Query("select * from `transaction` where user_id = :userId")
    override fun listTransactionsByUser(userId: String): Flowable<List<Transaction>>

    @Update
    override fun updateTransaction(transaction: Transaction): Int

    @Delete
    override fun deleteTransaction(transaction: Transaction): Int

    @Query("delete from `transaction`")
    override fun truncate()
}