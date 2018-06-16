package br.com.alissonfpmorais.tiralama.main.components.addcategory.internal

import br.com.alissonfpmorais.tiralama.common.data.local.entity.FlowType
import br.com.alissonfpmorais.tiralama.common.validateCategoryName
import com.spotify.mobius.Effects
import com.spotify.mobius.Next

typealias NextAddCategory = Next<AddCategoryModel, AddCategoryEffect>

fun addCategoryUpdate(model: AddCategoryModel, event: AddCategoryEvent): NextAddCategory {
    return when (event) {
        is CategoryNameInputChanged -> onCategoryNameInputChanged(model, event.categoryName, event.errorMsg)
        is CategoryTypeInputChanged -> onCategoryTypeInputChanged(model, event.categoryType, event.categoryTypePos)
        is SaveButtonClicked -> onSaveButtonClicked(model, event.categoryColor)
        is SaveSuccessful -> onSaveSuccessful()
        is SaveFailed -> onSaveFailed(model, event.errorMsg)
        is NavigatedToCategoriesScreen -> onNavigatedToCategoriesScreen()
        is ShowedSaveFailed -> onShowedSaveFailed()
    }
}

fun isAbleToSave(categoryName: String, isCategoryNameValid: Boolean): Boolean = isCategoryNameValid && categoryName != ""

fun onCategoryNameInputChanged(model: AddCategoryModel, categoryName: String, errorMsg: String): NextAddCategory {
    val isCategoryNameValid = validateCategoryName(categoryName)
    val canSave = isAbleToSave(categoryName, isCategoryNameValid)

    return Next.next(model.copy(
            categoryName = categoryName,
            isCategoryNameValid = isCategoryNameValid,
            categoryNameErrMsg = errorMsg,
            canSave = canSave))
}

fun onCategoryTypeInputChanged(model: AddCategoryModel, categoryType: FlowType, categoryTypePos: Int): NextAddCategory {
    return Next.next(model.copy(categoryType = categoryType, categoryTypePos = categoryTypePos))
}

fun onSaveButtonClicked(model: AddCategoryModel, categoryColor: Int): NextAddCategory {
    return if (model.canSave && !model.isSaving) {
        Next.next(model.copy(categoryColor = categoryColor, canSave = false, isSaving = true),
                Effects.effects(AttemptToSave(model.categoryName, categoryColor, model.categoryType)))
    } else {
        Next.next(model.copy(categoryColor = categoryColor))
    }
}

fun onSaveSuccessful(): NextAddCategory = Next.dispatch(Effects.effects(NavigateToCategoriesScreen))

fun onSaveFailed(model: AddCategoryModel, errorMsg: String): NextAddCategory {
    return Next.next(model.copy(canSave = true, isSaving = false),
            Effects.effects(ShowSaveFailed(errorMsg)))
}

fun onNavigatedToCategoriesScreen(): NextAddCategory = Next.next(AddCategoryModel())

fun onShowedSaveFailed(): NextAddCategory = Next.noChange()