package br.com.alissonfpmorais.tiralama.main.components.listcategories


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
import br.com.alissonfpmorais.tiralama.main.components.listcategories.external.CategoriesViewModel
import br.com.alissonfpmorais.tiralama.main.components.listcategories.external.categoriesHandler
import br.com.alissonfpmorais.tiralama.main.components.listcategories.internal.*
import com.jakewharton.rxbinding2.view.RxView
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_categories.*

typealias CategoriesLoopBuilder = MobiusLoop.Builder<CategoriesModel, CategoriesEvent, CategoriesEffect>
typealias CategoriesLoopController = MobiusLoop.Controller<CategoriesModel, CategoriesEvent>

class CategoriesFragment : MobiusFragment<CategoriesModel, CategoriesEvent, CategoriesEffect>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun getMobiusLoop(activity: AppCompatActivity, navController: NavController): CategoriesLoopBuilder {
        return RxMobius.loop(::categoriesUpdate, categoriesHandler(navController))
    }

    override fun getMobiusController(loop: CategoriesLoopBuilder): CategoriesLoopController {
        return MobiusAndroid.controller(loop, CategoriesModel)
    }

    override fun getViewModel(activity: AppCompatActivity): CustomViewModel<CategoriesModel> {
        return ViewModelProviders.of(activity).get(CategoriesViewModel::class.java)
    }

    override fun uiHandler(modelStream: Observable<CategoriesModel>): Observable<CategoriesEvent> {
        val modelDisposable = modelStream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { model ->
                    if (isViewModelInitialized()) viewModel.setModel(model)
                    render(model)
                }

        return RxView.clicks(addCategoryBt)
                .observeOn(Schedulers.computation())
                .map { FloatingButtonClicked as CategoriesEvent }
                .doOnDispose { modelDisposable.dispose() }
    }

    override fun init(model: CategoriesModel) { }

    override fun render(model: CategoriesModel) { }

//    override fun onResume() {
//        val disposable = DatabaseHolder.getSingleInstance(context!!).toObservable()
//                .flatMap { database -> Observable.fromIterable(database.categoryDao().listCategories()) }
//                .subscribe { Log.d("Categories", "Category name: ${it.name}") }
//
//        super.onResume()
//    }
}
