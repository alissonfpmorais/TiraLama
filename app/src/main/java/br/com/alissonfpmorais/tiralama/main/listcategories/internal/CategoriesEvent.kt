package br.com.alissonfpmorais.tiralama.main.listcategories.internal

sealed class CategoriesEvent

object FloatingButtonClicked : CategoriesEvent()
object NavigatedToAddCategoryScreen : CategoriesEvent()