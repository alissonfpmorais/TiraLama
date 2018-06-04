package br.com.alissonfpmorais.tiralama.common

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.rx2.RxConnectables
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class MobiusFragment<M, E, F> : Fragment() {
    lateinit var viewModel: CustomViewModel<M>

    abstract val loop: MobiusLoop.Builder<M, E, F>

    abstract val controller: MobiusLoop.Controller<M, E>

    val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller.connect(RxConnectables.fromTransformer(this::uiHandler))

        if (!isViewModelInitialized()) viewModel = getViewModel(activity as AppCompatActivity)

        viewModel.getLiveData().observe(this,
                Observer { model ->
                    model?.let {
                        if (!controller.isRunning) controller.replaceModel(it)
                    }
                })
    }

    override fun onResume() {
        super.onResume()
        controller.start()
    }

    override fun onPause() {
        super.onPause()
        controller.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.disconnect()
        if (!disposables.isDisposed && disposables.size() > 0) disposables.dispose()
    }

    abstract fun getViewModel(activity: AppCompatActivity): CustomViewModel<M>

    abstract fun uiHandler(modelStream: Observable<M>): Observable<E>

    abstract fun render(model: M)

    fun isViewModelInitialized(): Boolean = this::viewModel.isInitialized
}