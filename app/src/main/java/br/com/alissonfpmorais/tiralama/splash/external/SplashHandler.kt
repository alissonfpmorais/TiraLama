package br.com.alissonfpmorais.tiralama.splash.external

import br.com.alissonfpmorais.tiralama.splash.internal.LoadResources
import br.com.alissonfpmorais.tiralama.splash.internal.SplashEffect
import br.com.alissonfpmorais.tiralama.splash.internal.Finish
import br.com.alissonfpmorais.tiralama.splash.internal.SplashEvent
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun splashHandler(effectStream: Observable<SplashEffect>): Observable<SplashEvent> {
    return effectStream.flatMap { effect ->
        when (effect) {
            is LoadResources -> loadResources()
        }
    }
}

fun loadResources(): Observable<SplashEvent> {
    val delay = 2000L
    val unit = TimeUnit.MILLISECONDS

    return Observable
        .timer(delay, unit)
        .map { Finish }
}