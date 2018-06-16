package br.com.alissonfpmorais.tiralama.main.components.addtransaction


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.common.CustomViewModel
import br.com.alissonfpmorais.tiralama.common.MobiusFragment
import br.com.alissonfpmorais.tiralama.main.components.addtransaction.external.AddTransactionViewModel
import br.com.alissonfpmorais.tiralama.main.components.addtransaction.external.addTransactionHandler
import br.com.alissonfpmorais.tiralama.main.components.addtransaction.internal.*
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_add_transaction.*
import java.util.concurrent.TimeUnit

typealias AddTransactionLoopBuilder = MobiusLoop.Builder<AddTransactionModel, AddTransactionEvent, AddTransactionEffect>
typealias AddTransactionLoopController = MobiusLoop.Controller<AddTransactionModel, AddTransactionEvent>

class AddTransactionFragment : MobiusFragment<AddTransactionModel, AddTransactionEvent, AddTransactionEffect>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_transaction, container, false)
    }

    override fun getMobiusLoop(activity: AppCompatActivity, navController: NavController): AddTransactionLoopBuilder {
        return RxMobius.loop(::addTransactionUpdate, addTransactionHandler(activity, navController))
    }

    override fun getMobiusController(loop: AddTransactionLoopBuilder): AddTransactionLoopController {
        return MobiusAndroid.controller(loop, AddTransactionModel())
    }

    override fun getViewModel(activity: AppCompatActivity): CustomViewModel<AddTransactionModel> {
        return ViewModelProviders.of(activity).get(AddTransactionViewModel::class.java)
    }

    override fun uiHandler(modelStream: Observable<AddTransactionModel>): Observable<AddTransactionEvent> {
        val modelDisposable = modelStream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { model ->
                    if (isViewModelInitialized()) viewModel.setModel(model)
                    render(model)
                }

        val nameStream = RxTextView.textChanges(transactionNameInput)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map { text -> TransactionNameInputChanged(text.toString(), getString(R.string.transaction_name_failed_msg)) }

        val valueStream = RxTextView.textChanges(transactionValueInput)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map { text -> TransactionValueInputChanged(text.toString(), getString(R.string.transaction_value_failed_msg)) }

        val saveClickStream = RxView.clicks(saveBt)
                .map { SaveButtonClicked }

        val streams = listOf(nameStream, valueStream, saveClickStream)

        return Observable.merge(streams)
                .observeOn(Schedulers.computation())
                .doOnDispose { modelDisposable.dispose() }
    }

//    override fun init(model: AddTransactionModel) {
//        transactionNameInput?.setText(model.name)
//        transactionValueInput?.setText(model.value)
//
//        render(model)
//    }

    override fun init(model: AddTransactionModel) { }

    override fun render(model: AddTransactionModel) {
        transactionNameInput?.isEnabled = !model.isSaving
        transactionValueInput?.isEnabled = !model.isSaving
        saveBt?.isEnabled = !model.isSaving

        if (!model.isNameValid && transactionNameInput?.text.toString() != "") transactionNameLayout?.error = model.nameErrMsg
        else transactionNameLayout?.error = null

        if (!model.isValueValid && transactionValueInput?.text.toString() != "") transactionValueLayout?.error = model.valueErrMsg
        else transactionValueLayout?.error = null
    }
}
