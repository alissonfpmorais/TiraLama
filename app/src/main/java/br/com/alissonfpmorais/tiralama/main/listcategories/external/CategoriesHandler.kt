package br.com.alissonfpmorais.tiralama.main.listcategories.external

import androidx.navigation.NavController
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.main.listcategories.internal.CategoriesEffect
import br.com.alissonfpmorais.tiralama.main.listcategories.internal.CategoriesEvent
import br.com.alissonfpmorais.tiralama.main.listcategories.internal.NavigateToAddCategoryScreen
import br.com.alissonfpmorais.tiralama.main.listcategories.internal.NavigatedToAddCategoryScreen
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

typealias CategoriesHandler = (Observable<CategoriesEffect>) -> Observable<CategoriesEvent>

fun categoriesHandler(navController: NavController): CategoriesHandler {
    return fun(effectStream: Observable<CategoriesEffect>): Observable<CategoriesEvent> {
        return effectStream
                .observeOn(Schedulers.computation())
                .flatMap { effect ->
                    when (effect) {
                        is NavigateToAddCategoryScreen -> onNavigateToAddCategoryScreen(navController)
                    }
                }
    }
}

fun onNavigateToAddCategoryScreen(navController: NavController): Observable<CategoriesEvent> {
    return Observable.just(NavigatedToAddCategoryScreen as CategoriesEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { navController.navigate(R.id.addCategoryFragment) }
}