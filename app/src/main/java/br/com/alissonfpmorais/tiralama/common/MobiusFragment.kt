package br.com.alissonfpmorais.tiralama.common

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.rx2.RxConnectables
import io.reactivex.Observable

abstract class MobiusFragment<M, E, F> : Fragment() {
    lateinit var viewModel: CustomViewModel<M>

    private lateinit var controller: MobiusLoop.Controller<M, E>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loop: MobiusLoop.Builder<M, E, F> = getMobiusLoop(activity as AppCompatActivity)
        controller = getMobiusController(loop)

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
        init(controller.model)
        controller.start()
    }

    override fun onPause() {
        super.onPause()
        controller.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.disconnect()
    }

    abstract fun getMobiusLoop(activity: AppCompatActivity): MobiusLoop.Builder<M, E, F>

    abstract fun getMobiusController(loop: MobiusLoop.Builder<M, E, F>): MobiusLoop.Controller<M, E>

    abstract fun getViewModel(activity: AppCompatActivity): CustomViewModel<M>

    abstract fun uiHandler(modelStream: Observable<M>): Observable<E>

    abstract fun init(model: M)

    abstract fun render(model: M)

    fun isViewModelInitialized(): Boolean = this::viewModel.isInitialized
}