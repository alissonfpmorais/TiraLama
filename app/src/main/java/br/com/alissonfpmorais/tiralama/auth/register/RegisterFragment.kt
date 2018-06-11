package br.com.alissonfpmorais.tiralama.auth.register


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.auth.register.external.RegisterViewModel
import br.com.alissonfpmorais.tiralama.auth.register.external.registerHandler
import br.com.alissonfpmorais.tiralama.auth.register.internal.*
import br.com.alissonfpmorais.tiralama.common.CustomViewModel
import br.com.alissonfpmorais.tiralama.common.MobiusFragment
import br.com.alissonfpmorais.tiralama.common.NavigationHost
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : MobiusFragment<RegisterModel, RegisterEvent, RegisterEffect>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun getMobiusLoop(activity: AppCompatActivity): MobiusLoop.Builder<RegisterModel, RegisterEvent, RegisterEffect> {
        return RxMobius.loop(::registerUpdate, registerHandler(activity, activity as NavigationHost))
    }

    override fun getMobiusController(loop: MobiusLoop.Builder<RegisterModel, RegisterEvent, RegisterEffect>): MobiusLoop.Controller<RegisterModel, RegisterEvent> {
        return MobiusAndroid.controller(loop, RegisterModel())
    }

    override fun getViewModel(activity: AppCompatActivity): CustomViewModel<RegisterModel> =
            ViewModelProviders.of(activity).get(RegisterViewModel::class.java)

    override fun uiHandler(modelStream: Observable<RegisterModel>): Observable<RegisterEvent> {
        val modelDisposable = modelStream.observeOn(AndroidSchedulers.mainThread())
                .subscribe { model ->
                    if (isViewModelInitialized()) viewModel.setModel(model)
                    render(model)
                }

        val usernameStream = RxTextView.textChanges(usernameInput)
                .observeOn(Schedulers.computation())
                .debounce(300, TimeUnit.MILLISECONDS)
                .map { text -> UsernameInputChanged(text.toString(), getString(R.string.username_error_msg)) }

        val passwordTextStream = RxTextView.textChanges(passwordInput)
                .observeOn(Schedulers.computation())
                .debounce(300, TimeUnit.MILLISECONDS)
                .map { text -> text.toString() }

        val confirmationTextStream = RxTextView.textChanges(confirmationInput)
                .observeOn(Schedulers.computation())
                .debounce(300, TimeUnit.MILLISECONDS)
                .map { text -> text.toString() }

        val passwordStream = Observable
                .combineLatest<String, String, RegisterEvent>(
                        passwordTextStream, confirmationTextStream, BiFunction { password, confirmation ->
                    PasswordInputChanged(password, confirmation, getString(R.string.password_error_msg), getString(R.string.confirmation_error_msg))
                })
                .observeOn(Schedulers.computation())

        val registerClickStream = RxView.clicks(registerBt)
                .observeOn(Schedulers.computation())
                .map { RegisterButtonClicked }

        val backClickStream = RxView.clicks(backBt)
                .observeOn(Schedulers.computation())
                .map { BackButtonClicked }

        val streams = listOf(usernameStream, passwordStream, registerClickStream, backClickStream)

        return Observable.merge(streams)
                .observeOn(Schedulers.computation())
                .doOnDispose { modelDisposable.dispose() }
    }

    override fun init(model: RegisterModel) {
        usernameInput?.setText(model.username)
        passwordInput?.setText(model.password)
        confirmationInput?.setText(model.confirmation)

        render(model)
    }

    override fun render(model: RegisterModel) {
        usernameInput?.isEnabled = !model.isRegistering
        passwordInput?.isEnabled = !model.isRegistering
        confirmationInput?.isEnabled = !model.isRegistering
        backBt?.isEnabled = !model.isRegistering
        registerBt?.isEnabled = model.canRegister && !model.isRegistering

        if (!model.isUsernameValid && usernameInput?.text.toString() != "") usernameLayout?.error = model.userErrMsg
        else usernameLayout?.error = null

        if (!model.isPasswordValid && passwordInput?.text.toString() != "") passwordLayout?.error = model.passErrMsg
        else passwordLayout?.error = null

        if (!model.isConfirmationValid && confirmationInput?.text.toString() != "") confirmationLayout?.error = model.confirmErrMsg
        else confirmationLayout?.error = null
    }
}
