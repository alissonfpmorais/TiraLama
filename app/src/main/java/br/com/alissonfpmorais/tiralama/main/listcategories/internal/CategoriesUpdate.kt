package br.com.alissonfpmorais.tiralama.main.listcategories.internal

import com.spotify.mobius.Effects
import com.spotify.mobius.Next

typealias NextCategories = Next<CategoriesModel, CategoriesEffect>

@Suppress("UNUSED_PARAMETER")
fun categoriesUpdate(model: CategoriesModel, event: CategoriesEvent): NextCategories {
    return when (event) {
        is FloatingButtonClicked -> onFloatingButtonClicked()
        is NavigatedToAddCategoryScreen -> onNavigatedToAddCategoryScreen()
    }
}

fun onFloatingButtonClicked(): NextCategories = Next.dispatch(Effects.effects(NavigateToAddCategoryScreen))

fun onNavigatedToAddCategoryScreen(): NextCategories = Next.noChange()