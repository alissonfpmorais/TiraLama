package br.com.alissonfpmorais.tiralama.splash.internal

import com.spotify.mobius.Effects
import com.spotify.mobius.Next

/**
 * This function handles all Activity lifecycle
 */
fun splashUpdate(model: SplashModel, event: SplashEvent): Next<SplashModel, SplashEffect> {
    return when (event) {
        is Start -> onStartEvent(model)
        is Finish -> onFinishEvent(model)
    }
}

/**
 * Called when Start event happens
 */
fun onStartEvent(model: SplashModel): Next<SplashModel, SplashEffect> {
    return Next.next(
        model.copy(step = Starting),
        Effects.effects(LoadResources))
}

/**
 * Called when Finish event happens
 */
fun onFinishEvent(model: SplashModel): Next<SplashModel, SplashEffect> {
    return if (model.step == Starting) Next.next(model.copy(step = Finishing))
    else Next.noChange()
}