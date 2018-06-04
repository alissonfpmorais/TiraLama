package br.com.alissonfpmorais.tiralama

import br.com.alissonfpmorais.tiralama.splash.internal.LoadResources
import br.com.alissonfpmorais.tiralama.splash.internal.SplashEffect
import br.com.alissonfpmorais.tiralama.splash.internal.Finish
import br.com.alissonfpmorais.tiralama.splash.internal.Start
import br.com.alissonfpmorais.tiralama.splash.internal.Finishing
import br.com.alissonfpmorais.tiralama.splash.internal.SplashModel
import br.com.alissonfpmorais.tiralama.splash.internal.Starting
import br.com.alissonfpmorais.tiralama.splash.internal.splashUpdate
import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test


class SplashUnitTest {
    private val spec = UpdateSpec(::splashUpdate)

    @Test
    fun checkSplashStart() {
        val model = SplashModel()
        val effect = LoadResources

        spec.given(model)
            .`when`(Start)
            .then(assertThatNext(
                hasModel(model.copy(Starting)),
                hasEffects(effect as SplashEffect)))
    }

    @Test
    fun checkSplashFinishBeforeStarts() {
        val model = SplashModel()

        spec.given(model)
            .`when`(Finish)
            .then(assertThatNext(
                hasNothing()))
    }

    @Test
    fun checkSplashFinishAfterStarts() {
        val model =
            SplashModel(Starting)

        spec.given(model)
            .`when`(Finish)
            .then(assertThatNext(
                hasModel(model.copy(Finishing))))
    }
}