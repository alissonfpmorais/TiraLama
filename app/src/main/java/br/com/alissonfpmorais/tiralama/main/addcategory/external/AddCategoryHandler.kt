package br.com.alissonfpmorais.tiralama.main.addcategory.external

import android.support.v7.app.AppCompatActivity
import androidx.navigation.NavController
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.common.data.local.AppDatabase
import br.com.alissonfpmorais.tiralama.common.data.local.DatabaseHolder
import br.com.alissonfpmorais.tiralama.common.data.local.entity.Category
import br.com.alissonfpmorais.tiralama.common.data.local.entity.FlowType
import br.com.alissonfpmorais.tiralama.common.makeToast
import br.com.alissonfpmorais.tiralama.main.addcategory.internal.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

typealias AddCategoryHandler = (Observable<AddCategoryEffect>) -> Observable<AddCategoryEvent>

fun addCategoryHandler(activity: AppCompatActivity, navController: NavController): AddCategoryHandler {
    return fun(effectStream: Observable<AddCategoryEffect>): Observable<AddCategoryEvent> {
        return effectStream
                .observeOn(Schedulers.computation())
                .flatMap { effect ->
                    when (effect) {
                        is AttemptToSave -> onAttemptToSave(activity, effect.categoryName, effect.categoryColor, effect.categoryType)
                        is NavigateToCategoriesScreen -> onNavigateToCategoriesScreen(navController)
                        is ShowSaveFailed -> onShowSaveFailed(activity, effect.errorMsg)
                    }
                }
    }
}

fun onAttemptToSave(activity: AppCompatActivity, categoryName: String, categoryColor: Int, categoryType: FlowType): Observable<AddCategoryEvent> {
    val singleDb: Single<AppDatabase> = DatabaseHolder.getSingleInstance(activity)

    return singleDb
            .map { database ->
                val user = database.userDao().listByLogged(true).first()
                val category = Category(name = categoryName, color = categoryColor, flowType = categoryType, userId = user.username)
                val id = database.categoryDao().insertCategory(category)

                if (id >= 0) SaveSuccessful
                else SaveFailed(activity.getString(R.string.save_failed_msg))
            }
            .onErrorReturn { SaveFailed(activity.getString(R.string.save_failed_msg)) }
            .toObservable()
}

fun onNavigateToCategoriesScreen(navController: NavController): Observable<AddCategoryEvent> {
    return Observable.just(NavigatedToCategoriesScreen as AddCategoryEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { navController.navigateUp()
                navController.currentDestination.id
            }
}

fun onShowSaveFailed(activity: AppCompatActivity, errorMsg: String): Observable<AddCategoryEvent> {
    return Observable.just(ShowedSaveFailed as AddCategoryEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { activity.makeToast(errorMsg) }
}