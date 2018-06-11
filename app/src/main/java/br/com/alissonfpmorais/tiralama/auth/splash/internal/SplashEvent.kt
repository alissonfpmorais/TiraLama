package br.com.alissonfpmorais.tiralama.auth.splash.internal

sealed class SplashEvent
object CheckUserPreviousLogged : SplashEvent()
object UserIsLogged : SplashEvent()
object UserIsNotLogged : SplashEvent()
object NavigatedToLoginScreen : SplashEvent()
object NavigatedToMainActivity : SplashEvent()