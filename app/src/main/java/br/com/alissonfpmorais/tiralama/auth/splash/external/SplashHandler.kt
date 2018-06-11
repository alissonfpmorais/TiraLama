package br.com.alissonfpmorais.tiralama.auth.splash.external

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import br.com.alissonfpmorais.tiralama.auth.login.LoginFragment
import br.com.alissonfpmorais.tiralama.auth.splash.internal.*
import br.com.alissonfpmorais.tiralama.common.NavigationHost
import br.com.alissonfpmorais.tiralama.common.changeActivity
import br.com.alissonfpmorais.tiralama.common.data.local.AppDatabase
import br.com.alissonfpmorais.tiralama.common.data.local.DatabaseHolder
import br.com.alissonfpmorais.tiralama.main.MainActivity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

typealias SplashHandler = (Observable<SplashEffect>) -> Observable<SplashEvent>

fun splashHandler(activity: AppCompatActivity, navHost: NavigationHost): SplashHandler {
    return fun (effectStream: Observable<SplashEffect>): Observable<SplashEvent> {
        return effectStream
                .observeOn(Schedulers.computation())
                .flatMap { effect ->
                    when (effect) {
                        is LookForLoggedUser -> onLookForLoggedUser(activity)
                        is NavigateToLoginScreen -> onNavigateToLoginScreen(navHost)
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

fun onNavigateToLoginScreen(navHost: NavigationHost): Observable<SplashEvent> {
    return Observable.just(NavigatedToLoginScreen as SplashEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { navHost.replaceFragment(LoginFragment(), false) }
}

fun onNavigateToMainActivity(activity: AppCompatActivity): Observable<SplashEvent> {
    return Observable.just(NavigatedToLoginScreen as SplashEvent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                activity.changeActivity(MainActivity::class.java)
                activity.finish()
            }
}