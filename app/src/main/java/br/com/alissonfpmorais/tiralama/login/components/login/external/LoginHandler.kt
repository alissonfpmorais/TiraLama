package br.com.alissonfpmorais.tiralama.login.components.login.external

import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.com.alissonfpmorais.tiralama.common.replaceFragment
import br.com.alissonfpmorais.tiralama.login.components.login.internal.*
import io.reactivex.Observable

fun loginHandler(activity: AppCompatActivity): (Observable<LoginEffect>) -> (Observable<LoginEvent>) {
    return fun (effectStream: Observable<LoginEffect>): Observable<LoginEvent> {
        return effectStream.flatMap { loginEffect ->
            when (loginEffect) {
                is AttemptToLogin -> onAttemptToLogin()
                is NavigateToMainScreen -> onNavigateToMainScreen(activity)
                is NavigateToRegisterScreen -> onNavigateToRegisterScreen(activity)
            }
        }
    }
}

fun onAttemptToLogin(): Observable<LoginEvent> {
    Log.d("loginHandler", "AttemptToLogin")
    TODO()
}

fun onNavigateToMainScreen(activity: AppCompatActivity): Observable<LoginEvent> {
    Log.d("loginHandler", "NavigateToMainScreen")
    TODO()
}

fun onNavigateToRegisterScreen(activity: AppCompatActivity): Observable<LoginEvent> {
    Log.d("loginHandler", "NavigateToRegisterScreen")
    TODO()
}