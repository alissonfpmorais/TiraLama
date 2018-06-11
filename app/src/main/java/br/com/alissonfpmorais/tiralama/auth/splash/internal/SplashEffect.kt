package br.com.alissonfpmorais.tiralama.auth.splash.internal

sealed class SplashEffect
object LookForLoggedUser : SplashEffect()
object NavigateToLoginScreen : SplashEffect()
object NavigateToMainActivity : SplashEffect()