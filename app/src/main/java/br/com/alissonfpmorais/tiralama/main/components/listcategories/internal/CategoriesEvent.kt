package br.com.alissonfpmorais.tiralama.main.components.listcategories.internal

sealed class CategoriesEvent

object FloatingButtonClicked : CategoriesEvent()
object NavigatedToAddCategoryScreen : CategoriesEvent()