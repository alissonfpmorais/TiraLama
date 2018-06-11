package br.com.alissonfpmorais.tiralama.auth.splash

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.auth.AuthScreen
import br.com.alissonfpmorais.tiralama.auth.SplashScreen
import br.com.alissonfpmorais.tiralama.common.CustomViewModel
import br.com.alissonfpmorais.tiralama.common.MobiusActivity
import br.com.alissonfpmorais.tiralama.auth.splash.external.SplashViewModel
import br.com.alissonfpmorais.tiralama.auth.splash.external.splashHandler
import br.com.alissonfpmorais.tiralama.auth.splash.internal.*
import br.com.alissonfpmorais.tiralama.common.MobiusFragment
import br.com.alissonfpmorais.tiralama.common.NavigationHost
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashFragment : MobiusFragment<SplashModel, SplashEvent, SplashEffect>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun getMobiusLoop(activity: AppCompatActivity): MobiusLoop.Builder<SplashModel, SplashEvent, SplashEffect> {
        return RxMobius.loop(::splashUpdate, splashHandler(activity, activity as NavigationHost))
    }

    override fun getMobiusController(loop: MobiusLoop.Builder<SplashModel, SplashEvent, SplashEffect>): MobiusLoop.Controller<SplashModel, SplashEvent> {
        return MobiusAndroid.controller(loop, SplashModel())
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

        return Observable.timer(1500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.computation())
                .map { CheckUserPreviousLogged as SplashEvent }
                .doOnDispose { modelDisposable.dispose() }
    }

    override fun init(model: SplashModel) { }

    override fun render(model: SplashModel) { }
}
