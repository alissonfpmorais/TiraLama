package br.com.alissonfpmorais.tiralama.main.listtransaction

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.common.CustomViewModel
import br.com.alissonfpmorais.tiralama.common.MobiusFragment
import br.com.alissonfpmorais.tiralama.common.toCurrency
import br.com.alissonfpmorais.tiralama.main.listtransaction.external.TransactionsViewModel
import br.com.alissonfpmorais.tiralama.main.listtransaction.external.transactionsHandler
import br.com.alissonfpmorais.tiralama.main.listtransaction.internal.*
import com.jakewharton.rxbinding2.view.RxView
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_transactions.*
import java.util.concurrent.TimeUnit

typealias TransactionsLoopBuilder = MobiusLoop.Builder<TransactionsModel, TransactionsEvent, TransactionsEffect>
typealias TransactionsLoopController = MobiusLoop.Controller<TransactionsModel, TransactionsEvent>

class TransactionsFragment : MobiusFragment<TransactionsModel, TransactionsEvent, TransactionsEffect>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        transactionList.adapter = TransactionsAdapter(activity as AppCompatActivity)
        transactionList.setHasFixedSize(true)
        transactionList.layoutManager = LinearLayoutManager(activity)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun getMobiusLoop(activity: AppCompatActivity, navController: NavController): TransactionsLoopBuilder {
        return RxMobius.loop(::transactionsUpdate, transactionsHandler(activity, navController, transactionList.adapter as TransactionsAdapter))
    }

    override fun getMobiusController(loop: TransactionsLoopBuilder): TransactionsLoopController {
        return MobiusAndroid.controller(loop, TransactionsModel())
    }

    override fun getViewModel(activity: AppCompatActivity): CustomViewModel<TransactionsModel> {
        return ViewModelProviders.of(activity).get(TransactionsViewModel::class.java)
    }

    override fun uiHandler(modelStream: Observable<TransactionsModel>): Observable<TransactionsEvent> {
        val modelDisposable = modelStream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { model ->
                    if (isViewModelInitialized()) viewModel.setModel(model)

                    Log.d("truta", "renderiza tio!")
                    render(model)
                }

        val requestTransactionsStreams = Observable
                .timer(100, TimeUnit.MILLISECONDS)
                .map { RequestUpdatableTransactionsList }

        val addClickStream = RxView.clicks(addTransactionBt)
                .map { FloatingButtonClicked as TransactionsEvent }

        val streams = listOf(requestTransactionsStreams, addClickStream)

        return Observable.merge(streams)
                .observeOn(Schedulers.computation())
                .doOnDispose { modelDisposable.dispose() }
    }

    override fun init(model: TransactionsModel) { }

    override fun render(model: TransactionsModel) {
        Log.d("truta", "renderizando")

        totalBalance?.text = model.totalBalance.toCurrency()
        positiveBalance?.text = model.positiveBalance.toCurrency()
        negativeBalance?.text = model.negativeBalance.toCurrency()
    }
}