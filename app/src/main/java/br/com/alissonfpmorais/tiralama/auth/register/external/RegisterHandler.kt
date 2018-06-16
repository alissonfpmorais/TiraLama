package br.com.alissonfpmorais.tiralama.auth.register.external

import android.support.v7.app.AppCompatActivity
import androidx.navigation.NavController
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.auth.register.internal.*
import br.com.alissonfpmorais.tiralama.common.data.local.AppDatabase
import br.com.alissonfpmorais.tiralama.common.data.local.DatabaseHolder
import br.com.alissonfpmorais.tiralama.common.data.local.entity.User
import br.com.alissonfpmorais.tiralama.common.makeToast
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

typealias RegisterHandler = (Observable<RegisterEffect>) -> Observable<RegisterEvent>

fun registerHandler(activity: AppCompatActivity, navController: NavController): RegisterHandler {
    return fun(effectStream: Observable<RegisterEffect>): Observable<RegisterEvent> {
        return effectStream
                .observeOn(Schedulers.computation())
                .flatMap { effect ->
                    when(effect) {
                        is AttemptToRegister -> onAttemptToRegister(activity, effect.username, effect.password)
                        is NavigateToLoginScreen -> onNavigateToLoginScreen(navController)
                        is ShowRegisterFailed -> onShowRegisterFailed(activity, effect.errorMsg)
                    }
                }
    }
}

fun onAttemptToRegister(activity: AppCompatActivity, username: String, password: String): Observable<RegisterEvent> {
    val singleDb: Single<AppDatabase> = DatabaseHolder.getSingleInstance(activity)
    val user = User(username = username, password = password, isLogged = false)

    return singleDb.map { database -> database.userDao().insertUser(user) }
            .onErrorReturn { -1 }
            .map { id ->
                if (id >= 0) RegisterSuccessful
                else RegisterFailed(activity.getString(R.string.register_failed_msg))
            }
            .toObservable()
}

fun onNavigateToLoginScreen(navController: NavController): Observable<RegisterEvent> {
    return Observable.just(NavigatedToLoginScreen as RegisterEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { navController.navigateUp() }
}

fun onShowRegisterFailed(activity: AppCompatActivity, errorMsg: String): Observable<RegisterEvent> {
    return Observable.just(ShowedRegisterFailed as RegisterEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { activity.makeToast(errorMsg) }
}