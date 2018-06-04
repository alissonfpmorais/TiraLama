package br.com.alissonfpmorais.tiralama.splash.internal

sealed class SplashEvent
object Start : SplashEvent()
object Finish : SplashEvent()