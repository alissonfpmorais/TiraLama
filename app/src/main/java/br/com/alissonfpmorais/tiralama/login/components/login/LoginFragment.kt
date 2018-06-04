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
import br.com.alissonfpmorais.tiralama.login.components.login.internal.*
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_login.*

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

    override fun getViewModel(fragment: Fragment): CustomViewModel<LoginModel> =
            ViewModelProviders.of(fragment).get(LoginViewModel::class.java)

    override fun uiHandler(modelStream: Observable<LoginModel>): Observable<LoginEvent> {
        val modelDisposable = modelStream.observeOn(AndroidSchedulers.mainThread())
                .subscribe { model ->
                    if (isViewModelInitialized()) viewModel.setModel(model)
                    render(model)
                }

        disposables.add(modelDisposable)

        val previousLoggedStream = Observable.just(1)
                .map { UserPreviousLogged }

        val usernameStream = RxTextView.textChanges(usernameInput)
                .map { text -> UsernameInputChanged(text.toString()) }

        val passwordStream = RxTextView.textChanges(passwordInput)
                .map { text -> PasswordInputChanged(text.toString()) }

        val loginClickStream = RxView.clicks(loginBt)
                .map {
                    val username = usernameInput.text.toString()
                    val password = passwordInput.text.toString()

                    LoginButtonClicked(username, password)
                }

        val registerClickStream = RxView.clicks(registerBt)
                .map { RegisterButtonClicked }

        val streams = listOf(previousLoggedStream, usernameStream, passwordStream, loginClickStream, registerClickStream)

        return Observable.merge(streams)
    }

    override fun render(model: LoginModel) {
        usernameInput.setText(model.username)
        passwordInput.setText(model.password)

        usernameInput.isEnabled = !model.isLogging
        passwordInput.isEnabled = !model.isLogging
        registerBt.isEnabled = !model.isLogging
        loginBt.isEnabled = model.canLogin && !model.isLogging

        if (!model.isUsernameValid) usernameLayout.error = model.usernameErrorMsg
        else usernameLayout.error = null

        if (!model.isPasswordValid) passwordLayout.error = model.passwordErrorMsg
        else passwordLayout.error = null
    }
}
