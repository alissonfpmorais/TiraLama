package br.com.alissonfpmorais.tiralama.common.data.local.entity

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import br.com.alissonfpmorais.tiralama.common.data.local.converter.FlowConverter

sealed class FlowType
object OutOfFlow : FlowType()
object FlowIn: FlowType()
object FlowOut: FlowType()
object FlowOutMaybeIn: FlowType()

@Entity(
        tableName = "category",
        indices = [(Index(value = ["user_id"]))],
        foreignKeys = [
            (ForeignKey(entity = User::class, parentColumns = ["username"], childColumns = ["user_id"], onDelete = CASCADE))
        ]
)
@TypeConverters(FlowConverter::class)
data class Category(
        @PrimaryKey(autoGenerate = true) val id: Int? = null,
        val name: String,
        val color: Int,
        val flowType: FlowType,
        @ColumnInfo(name = "user_id") val userId: String)