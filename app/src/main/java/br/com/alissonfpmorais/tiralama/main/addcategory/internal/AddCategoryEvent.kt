package br.com.alissonfpmorais.tiralama.main.addcategory.internal

import br.com.alissonfpmorais.tiralama.common.data.local.entity.FlowType

sealed class AddCategoryEvent

data class CategoryNameInputChanged(val categoryName: String, val errorMsg: String) : AddCategoryEvent()
data class CategoryTypeInputChanged(val categoryType: FlowType, val categoryTypePos: Int) : AddCategoryEvent()
data class SaveButtonClicked(val categoryColor: Int) : AddCategoryEvent()
object SaveSuccessful : AddCategoryEvent()
data class SaveFailed(val errorMsg: String) : AddCategoryEvent()
object NavigatedToCategoriesScreen : AddCategoryEvent()
object ShowedSaveFailed : AddCategoryEvent()