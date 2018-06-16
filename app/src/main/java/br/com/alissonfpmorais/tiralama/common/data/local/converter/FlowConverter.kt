package br.com.alissonfpmorais.tiralama.common.data.local.converter

import android.arch.persistence.room.TypeConverter
import br.com.alissonfpmorais.tiralama.common.data.local.entity.*

class FlowConverter {
    @TypeConverter
    fun toFlowType(type: Int): FlowType = when (type) {
        1 -> OutOfFlow
        2 -> FlowIn
        3 -> FlowOut
        else -> FlowOutMaybeIn
    }

    @TypeConverter
    fun toInt(flowType: FlowType): Int = when (flowType) {
        is OutOfFlow -> 1
        is FlowIn -> 2
        is FlowOut -> 3
        is FlowOutMaybeIn -> 4
    }
}