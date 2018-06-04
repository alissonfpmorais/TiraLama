package br.com.alissonfpmorais.tiralama.common.data.local.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

sealed class FlowType
object FlowIn: FlowType()
object FlowOut: FlowType()
object FlowOutWithIn: FlowType()

@Entity(
    tableName = "category",
    foreignKeys = [
        (ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"]))
    ]
)
data class Category(
    @PrimaryKey val id: String,
    val name: String,
    val flowType: FlowType,
    @ColumnInfo(name = "user_id") val userId: String)