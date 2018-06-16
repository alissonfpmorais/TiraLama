package br.com.alissonfpmorais.tiralama.main.components.listtransaction.external

import android.support.v7.app.AppCompatActivity
import androidx.navigation.NavController
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.common.data.local.DatabaseHolder
import br.com.alissonfpmorais.tiralama.common.data.local.entity.Transaction
import br.com.alissonfpmorais.tiralama.main.components.listtransaction.TransactionsAdapter
import br.com.alissonfpmorais.tiralama.main.components.listtransaction.internal.*
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
                        is FillTransactionsList -> onFillTransactionsList(activity, adapter)
                        is NavigateToAddTransactionScreen -> onNavigateToAddCategoryScreen(navController)
                    }
                }
    }
}

fun onFillTransactionsList(activity: AppCompatActivity, adapter: TransactionsAdapter): Observable<TransactionsEvent> {
    val singleDb = DatabaseHolder.getSingleInstance(activity)

    return singleDb
            .toObservable()
            .flatMap { database ->
                val user = database.userDao().listByLogged(true).first()
                database.transactionDao().listTransactionsByUser(user.username).toObservable()
            }
            .onErrorReturn { listOf() }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { transactions -> if (transactions.isNotEmpty()) adapter.data = transactions }
            .map { transactions -> NewTransactionsArrive(transactions) as TransactionsEvent }
}

fun onNavigateToAddCategoryScreen(navController: NavController): Observable<TransactionsEvent> {
    return Observable.just(NavigatedToAddTransactionScreen as TransactionsEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { navController.navigate(R.id.addTransactionFragment) }
}