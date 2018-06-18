package br.com.alissonfpmorais.tiralama.main.listtransaction.external

import android.support.v7.app.AppCompatActivity
import android.util.Log
import androidx.navigation.NavController
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.common.data.local.DatabaseHolder
import br.com.alissonfpmorais.tiralama.common.data.local.entity.Transaction
import br.com.alissonfpmorais.tiralama.main.listtransaction.TransactionsAdapter
import br.com.alissonfpmorais.tiralama.main.listtransaction.internal.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

typealias TransactionsHandler = (Observable<TransactionsEffect>) -> Observable<TransactionsEvent>

fun transactionsHandler(activity: AppCompatActivity, navController: NavController, adapter: TransactionsAdapter): TransactionsHandler {
    return fun(effectStream: Observable<TransactionsEffect>): Observable<TransactionsEvent> {
        return effectStream
                .observeOn(Schedulers.computation())
                .flatMap { effect ->
                    when (effect) {
                        is UpdateTransactionsList -> onUpdateTransactionsList(activity, adapter)
                        is NavigateToAddTransactionScreen -> onNavigateToAddCategoryScreen(navController)
                    }
                }
    }
}

fun getTransactions(activity: AppCompatActivity): Observable<List<Transaction>> {
    val singleDb = DatabaseHolder.getSingleInstance(activity)

    return singleDb
            .toObservable()
            .flatMap { database ->
                val user = database.userDao().listByLogged(true).first()
                database.transactionDao().listTransactionsByUser(user.username).toObservable()
            }
            .onErrorReturn { listOf() }
}

fun onUpdateTransactionsList(activity: AppCompatActivity, adapter: TransactionsAdapter): Observable<TransactionsEvent> {
    return getTransactions(activity)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { Log.d("truta", "onUpdateTRansactionsList") }
            .doOnNext { transactions -> adapter.data = transactions }
            .doOnNext { Log.d("truta", "adapter atualizado") }
            .map { transactions -> TransactionsListUpdated(transactions) as TransactionsEvent }
            .doOnNext { Log.d("truta", "event: $it") }
}

fun onNavigateToAddCategoryScreen(navController: NavController): Observable<TransactionsEvent> {
    return Observable.just(NavigatedToAddTransactionScreen as TransactionsEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { navController.navigate(R.id.addTransactionFragment) }
}