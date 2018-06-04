package br.com.alissonfpmorais.tiralama.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.common.CustomViewModel
import br.com.alissonfpmorais.tiralama.common.MobiusActivity
import br.com.alissonfpmorais.tiralama.login.external.AuthViewModel
import br.com.alissonfpmorais.tiralama.login.external.authHandler
import br.com.alissonfpmorais.tiralama.login.internal.*
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class AuthActivity : MobiusActivity<AuthModel, AuthEvent, AuthEffect>() {
    override val loop: MobiusLoop.Builder<AuthModel, AuthEvent, AuthEffect> =
        RxMobius.loop(::authUpdate, ::authHandler)

    override val controller: MobiusLoop.Controller<AuthModel, AuthEvent> =
        MobiusAndroid.controller(loop, AuthModel())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override fun getViewModel(activity: AppCompatActivity): CustomViewModel<AuthModel> =
        ViewModelProviders.of(activity).get(AuthViewModel::class.java)

    override fun uiHandler(modelStream: Observable<AuthModel>): Observable<AuthEvent> {
        val modelDisposable = modelStream
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { model ->
                if (isViewModelInitialized()) viewModel.setModel(model)
                render(model)
            }

        disposables.add(modelDisposable)

        TODO("Add RxView and turn to stream")
    }

    override fun render(model: AuthModel) {
        TODO()
    }
}
