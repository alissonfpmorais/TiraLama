package br.com.alissonfpmorais.tiralama.main.components.addtransaction.external

import android.support.v7.app.AppCompatActivity
import androidx.navigation.NavController
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.common.data.local.AppDatabase
import br.com.alissonfpmorais.tiralama.common.data.local.DatabaseHolder
import br.com.alissonfpmorais.tiralama.common.data.local.entity.Transaction
import br.com.alissonfpmorais.tiralama.common.makeToast
import br.com.alissonfpmorais.tiralama.main.components.addtransaction.internal.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

typealias AddTransactionHandler = (Observable<AddTransactionEffect>) -> Observable<AddTransactionEvent>

fun addTransactionHandler(activity: AppCompatActivity, navController: NavController): AddTransactionHandler {
    return fun(effectStream: Observable<AddTransactionEffect>): Observable<AddTransactionEvent> {
        return effectStream
                .observeOn(Schedulers.computation())
                .flatMap { effect ->
                    when (effect) {
                        is AttemptToSave -> onAttemptToSave(activity, effect.name, effect.value)
                        is NavigateToTransactionsScreen -> onNavigateToTransactionsScreen(navController)
                        is ShowSaveFailed -> onShowSaveFailed(activity, effect.errorMsg)
                    }
                }
    }
}

fun onAttemptToSave(activity: AppCompatActivity, name: String, value: String): Observable<AddTransactionEvent> {
    val singleDb: Single<AppDatabase> = DatabaseHolder.getSingleInstance(activity)

    return singleDb
            .map { database ->
                val user = database.userDao().listByLogged(true).first()
                val transaction = Transaction(name = name, value = value.toFloat(),date = Date(), userId = user.username)
                val id = database.transactionDao().insert(transaction)

                if (id >= 0) SaveSuccessful
                else SaveFailed(activity.getString(R.string.save_failed_msg))

            }
            .onErrorReturn { SaveFailed(activity.getString(R.string.save_failed_msg)) }
            .toObservable()
}

fun onNavigateToTransactionsScreen(navController: NavController): Observable<AddTransactionEvent> {
    return Observable.just(NavigatedToTransactionsScreen as AddTransactionEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { navController.navigateUp() }
}

fun onShowSaveFailed(activity: AppCompatActivity, errorMsg: String): Observable<AddTransactionEvent> {
    return Observable.just(ShowedSaveFailed as AddTransactionEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { activity.makeToast(errorMsg) }
}