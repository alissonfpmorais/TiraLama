package br.com.alissonfpmorais.tiralama.common.data.local.entity

import android.arch.persistence.room.*
import br.com.alissonfpmorais.tiralama.common.data.local.converter.FlowConverter

sealed class FlowType
object FlowIn: FlowType()
object FlowOut: FlowType()
object FlowOutMaybeIn: FlowType()

@Entity(
    tableName = "category",
    foreignKeys = [
        (ForeignKey(entity = User::class, parentColumns = ["username"], childColumns = ["user_id"]))
    ]
)
@TypeConverters(FlowConverter::class)
data class Category(
        @PrimaryKey(autoGenerate = true) val id: Int? = null,
        val name: String,
        val flowType: FlowType,
        @ColumnInfo(name = "user_id") val userId: Int)