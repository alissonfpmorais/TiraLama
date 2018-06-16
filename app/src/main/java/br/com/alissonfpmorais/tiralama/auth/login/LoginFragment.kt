package br.com.alissonfpmorais.tiralama.auth.login


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.auth.login.external.LoginViewModel
import br.com.alissonfpmorais.tiralama.auth.login.external.loginHandler
import br.com.alissonfpmorais.tiralama.auth.login.internal.*
import br.com.alissonfpmorais.tiralama.common.CustomViewModel
import br.com.alissonfpmorais.tiralama.common.MobiusFragment
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.concurrent.TimeUnit

typealias LoginLoopBuilder = MobiusLoop.Builder<LoginModel, LoginEvent, LoginEffect>
typealias LoginLoopController = MobiusLoop.Controller<LoginModel, LoginEvent>

class LoginFragment : MobiusFragment<LoginModel, LoginEvent, LoginEffect>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun getMobiusLoop(activity: AppCompatActivity, navController: NavController): LoginLoopBuilder {
        return RxMobius.loop(::loginUpdate, loginHandler(activity, navController))
    }

    override fun getMobiusController(loop: LoginLoopBuilder): LoginLoopController {
        return MobiusAndroid.controller(loop, LoginModel())
    }

    override fun getViewModel(activity: AppCompatActivity): CustomViewModel<LoginModel> =
            ViewModelProviders.of(activity).get(LoginViewModel::class.java)

    override fun uiHandler(modelStream: Observable<LoginModel>): Observable<LoginEvent> {
        val modelDisposable = modelStream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { model ->
                    if (isViewModelInitialized()) viewModel.setModel(model)
                    render(model)
                }

        val usernameStream = RxTextView.textChanges(usernameInput)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map { text -> UsernameInputChanged(text.toString(), getString(R.string.username_error_msg)) }

        val passwordStream = RxTextView.textChanges(passwordInput)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map { text -> PasswordInputChanged(text.toString(), getString(R.string.password_error_msg)) }

        val loginClickStream = RxView.clicks(loginBt)
                .map { LoginButtonClicked }

        val registerClickStream = RxView.clicks(registerBt)
                .map { RegisterButtonClicked }

        val streams = listOf(usernameStream, passwordStream, loginClickStream, registerClickStream)

        return Observable.merge(streams)
                .observeOn(Schedulers.computation())
                .doOnDispose { modelDisposable.dispose() }
    }

    override fun init(model: LoginModel) {
        usernameInput?.setText(model.username)
        passwordInput?.setText(model.password)

        render(model)
    }

    override fun render(model: LoginModel) {
        usernameInput?.isEnabled = !model.isLogging
        passwordInput?.isEnabled = !model.isLogging
        registerBt?.isEnabled = !model.isLogging
        loginBt?.isEnabled = model.canLogin && !model.isLogging

        if (!model.isUsernameValid && usernameInput?.text.toString() != "") usernameLayout?.error = model.userErrMsg
        else usernameLayout?.error = null

        if (!model.isPasswordValid && passwordInput?.text.toString() != "") passwordLayout?.error = model.passErrMsg
        else passwordLayout?.error = null
    }
}
