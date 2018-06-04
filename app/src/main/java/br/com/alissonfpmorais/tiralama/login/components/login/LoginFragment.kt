package br.com.alissonfpmorais.tiralama.login.components.login


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.common.CustomViewModel
import br.com.alissonfpmorais.tiralama.common.MobiusFragment
import br.com.alissonfpmorais.tiralama.login.components.login.external.LoginViewModel
import br.com.alissonfpmorais.tiralama.login.components.login.external.loginHandler
import br.com.alissonfpmorais.tiralama.login.components.login.internal.LoginEffect
import br.com.alissonfpmorais.tiralama.login.components.login.internal.LoginEvent
import br.com.alissonfpmorais.tiralama.login.components.login.internal.LoginModel
import br.com.alissonfpmorais.tiralama.login.components.login.internal.loginUpdate
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * A simple [Fragment] subclass.
 *
 */
class LoginFragment : MobiusFragment<LoginModel, LoginEvent, LoginEffect>() {
    override val loop: MobiusLoop.Builder<LoginModel, LoginEvent, LoginEffect> =
            RxMobius.loop(::loginUpdate, loginHandler(activity as AppCompatActivity))

    override val controller: MobiusLoop.Controller<LoginModel, LoginEvent> =
            MobiusAndroid.controller(loop, LoginModel())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun getViewModel(activity: AppCompatActivity): CustomViewModel<LoginModel> =
            ViewModelProviders.of(this).get(LoginViewModel::class.java)

    override fun uiHandler(modelStream: Observable<LoginModel>): Observable<LoginEvent> {
        modelStream.observeOn(AndroidSchedulers.mainThread())
                .subscribe { model ->
                    if (isViewModelInitialized()) viewModel.setModel(model)
                    render(model)
                }

        TODO()
    }

    override fun render(model: LoginModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
