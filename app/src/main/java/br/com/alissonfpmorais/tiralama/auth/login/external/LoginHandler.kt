package br.com.alissonfpmorais.tiralama.auth.login.external

import android.support.v7.app.AppCompatActivity
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.auth.login.internal.*
import br.com.alissonfpmorais.tiralama.common.changeActivity
import br.com.alissonfpmorais.tiralama.common.data.local.AppDatabase
import br.com.alissonfpmorais.tiralama.common.data.local.DatabaseHolder
import br.com.alissonfpmorais.tiralama.common.data.local.entity.User
import br.com.alissonfpmorais.tiralama.common.makeToast
import br.com.alissonfpmorais.tiralama.main.MainActivity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

typealias LoginHandler = (Observable<LoginEffect>) -> (Observable<LoginEvent>)

fun loginHandler(activity: AppCompatActivity, navController: NavController): LoginHandler {
    return fun (effectStream: Observable<LoginEffect>): Observable<LoginEvent> {
        return effectStream
                .observeOn(Schedulers.computation())
                .flatMap { effect ->
                    when (effect) {
                        is AttemptToLogin -> onAttemptToLogin(activity, effect.username, effect.password)
                        is ShowLoginFailed -> onShowLoginFailed(activity, effect.errorMsg)
                        is NavigateToMainScreen -> onNavigateToMainScreen(activity)
                        is NavigateToRegisterScreen -> onNavigateToRegisterScreen(navController)
                    }
                }
    }
}

fun onAttemptToLogin(activity: AppCompatActivity, username: String, password: String): Observable<LoginEvent> {
    val singleDb: Single<AppDatabase> = DatabaseHolder.getSingleInstance(activity)

    return singleDb
            .map { database ->
                val users: List<User> = database.userDao().listByUsernameAndPassword(username, password)
                if (users.isNotEmpty()) {
                    val user = users.first().copy(isLogged = true)
                    database.userDao().update(user)

                    LoginSuccessful
                }
                else LoginFailed(activity.getString(R.string.login_failed_msg))
            }
            .toObservable()
}

fun onShowLoginFailed(activity: AppCompatActivity, errorMsg: String): Observable<LoginEvent> {
    return Observable.just(ShowedLoginFailed as LoginEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { activity.makeToast(errorMsg) }
}

fun onNavigateToMainScreen(activity: AppCompatActivity): Observable<LoginEvent> {
    return Observable.just(NavigatedToMainActivity as LoginEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                activity.changeActivity(MainActivity::class.java)
                activity.finish()
            }
}

fun onNavigateToRegisterScreen(navController: NavController): Observable<LoginEvent> {
    return Observable.just(NavigatedToRegisterScreen as LoginEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { navController.navigate(R.id.registerFragment) }
}