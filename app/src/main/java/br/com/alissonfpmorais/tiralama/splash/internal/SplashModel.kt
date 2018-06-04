package br.com.alissonfpmorais.tiralama.splash.internal

sealed class SplashStep
object None: SplashStep()
object Starting: SplashStep()
object Finishing: SplashStep()

/**
 * Represents the state of SplashActivity
 */
data class SplashModel(val step: SplashStep = None)

