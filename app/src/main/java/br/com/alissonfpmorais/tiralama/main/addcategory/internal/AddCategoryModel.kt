package br.com.alissonfpmorais.tiralama.main.addcategory.internal

import android.graphics.Color
import br.com.alissonfpmorais.tiralama.common.data.local.entity.FlowType
import br.com.alissonfpmorais.tiralama.common.data.local.entity.OutOfFlow
import br.com.alissonfpmorais.tiralama.common.random

fun getRandomColor(): Int {
    val red: Int = (0..256).random()
    val green: Int = (0..256).random()
    val blue: Int = (0..256).random()

    return Color.rgb(red, green, blue)
}

data class AddCategoryModel(
        val categoryName: String = "",
        val categoryColor: Int = getRandomColor(),
        val categoryType: FlowType = OutOfFlow,
        val categoryTypePos: Int = 0,
        val isCategoryNameValid: Boolean = true,
        val categoryNameErrMsg: String = "",
        val canSave: Boolean = false,
        val isSaving: Boolean = false)