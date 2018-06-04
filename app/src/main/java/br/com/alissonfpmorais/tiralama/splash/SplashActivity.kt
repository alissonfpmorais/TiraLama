package br.com.alissonfpmorais.tiralama.splash

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.common.CustomViewModel
import br.com.alissonfpmorais.tiralama.common.MobiusActivity
import br.com.alissonfpmorais.tiralama.common.changeActivity
import br.com.alissonfpmorais.tiralama.login.AuthActivity
import br.com.alissonfpmorais.tiralama.splash.external.SplashViewModel
import br.com.alissonfpmorais.tiralama.splash.external.splashHandler
import br.com.alissonfpmorais.tiralama.splash.internal.*
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class SplashActivity : MobiusActivity<SplashModel, SplashEvent, SplashEffect>() {
    override val loop: MobiusLoop.Builder<SplashModel, SplashEvent, SplashEffect> =
        RxMobius.loop(::splashUpdate, ::splashHandler)

    override val controller: MobiusLoop.Controller<SplashModel, SplashEvent> =
        MobiusAndroid.controller(loop, SplashModel())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun getViewModel(activity: AppCompatActivity): CustomViewModel<SplashModel> =
        ViewModelProviders.of(activity).get(SplashViewModel::class.java)

    override fun uiHandler(modelStream: Observable<SplashModel>): Observable<SplashEvent> {
        val modelDisposable = modelStream
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { model ->
                if (isViewModelInitialized()) viewModel.setModel(model)
                render(model)
            }

        disposables.add(modelDisposable)

        return Observable.just(Start)
    }

    override fun render(model: SplashModel) {
        val step = model.step

        when (step) {
            is Starting -> Toast.makeText(this.applicationContext, "Starting", Toast.LENGTH_SHORT).show()
            is Finishing -> changeActivity(AuthActivity::class.java)
        }
    }
}
