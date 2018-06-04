package br.com.alissonfpmorais.tiralama.common

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

abstract class CustomViewModel<T> : ViewModel() {
    private var liveData: MutableLiveData<T> = MutableLiveData()

    fun getLiveData(): LiveData<T> = liveData
    fun setModel(model: T) = liveData.postValue(model)
}