package br.com.alissonfpmorais.tiralama.main.components.addcategory


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.NavController
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.common.CustomViewModel
import br.com.alissonfpmorais.tiralama.common.MobiusFragment
import br.com.alissonfpmorais.tiralama.common.data.local.entity.*
import br.com.alissonfpmorais.tiralama.main.components.addcategory.external.AddCategoryViewModel
import br.com.alissonfpmorais.tiralama.main.components.addcategory.external.addCategoryHandler
import br.com.alissonfpmorais.tiralama.main.components.addcategory.internal.*
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_add_category.*
import java.util.concurrent.TimeUnit

typealias AddCategoryLoopBuilder = MobiusLoop.Builder<AddCategoryModel, AddCategoryEvent, AddCategoryEffect>
typealias AddCategoryLoopController = MobiusLoop.Controller<AddCategoryModel, AddCategoryEvent>

class AddCategoryFragment : MobiusFragment<AddCategoryModel, AddCategoryEvent, AddCategoryEffect>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val types = listOf(OutOfFlow, FlowIn, FlowOut, FlowOutMaybeIn)
        val adapter = ArrayAdapter(activity, android.R.layout.simple_dropdown_item_1line, types)
        categoryType.adapter = adapter

        super.onViewCreated(view, savedInstanceState)
    }

    override fun getMobiusLoop(activity: AppCompatActivity, navController: NavController): AddCategoryLoopBuilder {
        return RxMobius.loop(::addCategoryUpdate, addCategoryHandler(activity, navController))
    }

    override fun getMobiusController(loop: AddCategoryLoopBuilder): AddCategoryLoopController {
        return MobiusAndroid.controller(loop, AddCategoryModel())
    }

    override fun getViewModel(activity: AppCompatActivity): CustomViewModel<AddCategoryModel> {
        return ViewModelProviders.of(activity).get(AddCategoryViewModel::class.java)
    }

    override fun uiHandler(modelStream: Observable<AddCategoryModel>): Observable<AddCategoryEvent> {
        val modelDisposable = modelStream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { model ->
                    if (isViewModelInitialized()) viewModel.setModel(model)
                    render(model)
                }

        val typeStream = RxAdapterView.itemSelections(categoryType)
                .map { pos -> CategoryTypeInputChanged(categoryType.getItemAtPosition(pos) as FlowType, pos) }

        val nameStream = RxTextView.textChanges(categoryNameInput)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map { text -> CategoryNameInputChanged(text.toString(), getString(R.string.category_name_failed_msg)) }

        val saveClickStream = RxView.clicks(saveBt)
                .map { SaveButtonClicked(categoryColor.solidColor) }

        val streams = listOf(typeStream, nameStream, saveClickStream)

        return Observable.merge(streams)
                .observeOn(Schedulers.computation())
                .doOnDispose { modelDisposable.dispose() }
    }

    override fun init(model: AddCategoryModel) {
        categoryNameInput?.setText(model.categoryName)
        render(model)
    }

    override fun render(model: AddCategoryModel) {
        categoryColor?.setBackgroundColor(model.categoryColor)
        categoryType?.setSelection(model.categoryTypePos)

        categoryNameInput?.isEnabled = !model.isSaving
        categoryColor?.isEnabled = !model.isSaving
        categoryType?.isEnabled = !model.isSaving
        saveBt?.isEnabled = model.canSave && !model.isSaving

        if (!model.isCategoryNameValid && categoryNameInput?.text.toString() != "") categoryNameInput?.error = model.categoryNameErrMsg
        else categoryNameInput?.error = null
    }
}
