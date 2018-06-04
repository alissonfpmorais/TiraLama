package br.com.alissonfpmorais.tiralama.login.external

import br.com.alissonfpmorais.tiralama.login.internal.AuthEffect
import br.com.alissonfpmorais.tiralama.login.internal.AuthEvent
import io.reactivex.Observable

fun authHandler(effectStream: Observable<AuthEffect>): Observable<AuthEvent> {
    TODO()
}