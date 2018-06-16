package br.com.alissonfpmorais.tiralama.main.components.addcategory.internal

import br.com.alissonfpmorais.tiralama.common.data.local.entity.FlowType

sealed class AddCategoryEffect

data class AttemptToSave(val categoryName: String, val categoryColor: Int, val categoryType: FlowType) : AddCategoryEffect()
object NavigateToCategoriesScreen : AddCategoryEffect()
data class ShowSaveFailed(val errorMsg: String) : AddCategoryEffect()