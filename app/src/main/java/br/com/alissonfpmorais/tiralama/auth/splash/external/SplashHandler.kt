package br.com.alissonfpmorais.tiralama.auth.splash.external

import android.support.v7.app.AppCompatActivity
import androidx.navigation.NavController
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.auth.splash.internal.*
import br.com.alissonfpmorais.tiralama.common.changeActivity
import br.com.alissonfpmorais.tiralama.common.data.local.AppDatabase
import br.com.alissonfpmorais.tiralama.common.data.local.DatabaseHolder
import br.com.alissonfpmorais.tiralama.main.MainActivity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

typealias SplashHandler = (Observable<SplashEffect>) -> Observable<SplashEvent>

fun splashHandler(activity: AppCompatActivity, navController: NavController): SplashHandler {
    return fun (effectStream: Observable<SplashEffect>): Observable<SplashEvent> {
        return effectStream
                .observeOn(Schedulers.computation())
                .flatMap { effect ->
                    when (effect) {
                        is LookForLoggedUser -> onLookForLoggedUser(activity)
                        is NavigateToLoginScreen -> onNavigateToLoginScreen(navController)
                        is NavigateToMainActivity -> onNavigateToMainActivity(activity)
                    }
                }
    }
}

fun onLookForLoggedUser(activity: AppCompatActivity): Observable<SplashEvent> {
    val singleDb: Single<AppDatabase> = DatabaseHolder.getSingleInstance(activity)

    return singleDb.map { database -> database.userDao().listByLogged(true) }
            .map { users ->
                if (users.isNotEmpty()) UserIsLogged
                else UserIsNotLogged
            }
            .toObservable()
}

fun onNavigateToLoginScreen(navController: NavController): Observable<SplashEvent> {
    return Observable.just(NavigatedToLoginScreen as SplashEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { navController.navigate(R.id.action_splashFragment_to_loginFragment) }
}

fun onNavigateToMainActivity(activity: AppCompatActivity): Observable<SplashEvent> {
    return Observable.just(NavigatedToLoginScreen as SplashEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                activity.changeActivity(MainActivity::class.java)
                activity.finish()
            }
}