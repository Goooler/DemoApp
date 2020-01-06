package io.goooler.demoapp.main.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.goooler.demoapp.api.RetrofitHelper
import io.goooler.demoapp.base.BaseViewModel
import io.goooler.demoapp.main.api.MainApi
import io.goooler.demoapp.main.repository.MainRepository
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val repository by lazy(LazyThreadSafetyMode.NONE) {
        MainRepository(RetrofitHelper.createApiService(MainApi::class.java))
    }

    val title = MutableLiveData<String>()

    fun initData() {
        repository.getDemoAppInfo()
                .subscribeOn(Schedulers.io())
                .filter {
                    it.status
                }
                .map {
                    return@map it.entry!!.name.orEmpty()
                }
                .subscribe({
                    title.postValue(it)
                }, {
                    silentThrowable(it)
                })
                .let {
                    addDisposable(it)
                }
    }
}