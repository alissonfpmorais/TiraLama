package br.com.alissonfpmorais.tiralama.common.data.local.entity

import android.arch.persistence.room.*
import br.com.alissonfpmorais.tiralama.common.data.local.converter.DateConverter
import java.util.*

@Entity(
    tableName = "transaction",
    foreignKeys = [
        (ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["category_in_id"])),
        (ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["category_out_id"])),
        (ForeignKey(entity = User::class, parentColumns = ["username"], childColumns = ["user_id"]))
    ]
)
@TypeConverters(DateConverter::class)
data class Transaction(
        @PrimaryKey(autoGenerate = true) val id: Int? = null,
        val value: Float,
        val date: Date,
        @ColumnInfo(name = "category_in_id") val categoryInId: Int,
        @ColumnInfo(name = "category_out_id") val categoryOutId: Int,
        @ColumnInfo(name = "user_id") val userId: String)