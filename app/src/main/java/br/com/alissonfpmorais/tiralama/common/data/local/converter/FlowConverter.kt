package br.com.alissonfpmorais.tiralama.common.data.local.converter

import android.arch.persistence.room.TypeConverter
import br.com.alissonfpmorais.tiralama.common.data.local.entity.FlowIn
import br.com.alissonfpmorais.tiralama.common.data.local.entity.FlowOut
import br.com.alissonfpmorais.tiralama.common.data.local.entity.FlowOutMaybeIn
import br.com.alissonfpmorais.tiralama.common.data.local.entity.FlowType

class FlowConverter {
    @TypeConverter
    fun toFlowType(type: Int): FlowType = when (type) {
        1 -> FlowIn
        2 -> FlowOut
        else -> FlowOutMaybeIn
    }

    @TypeConverter
    fun toInt(flowType: FlowType): Int = when (flowType) {
        is FlowIn -> 1
        is FlowOut -> 2
        is FlowOutMaybeIn -> 3
    }
}