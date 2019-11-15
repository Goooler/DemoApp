package io.goooler.demoapp.main.vm

import android.app.Application
import androidx.databinding.ObservableField
import io.goooler.demoapp.R
import io.goooler.demoapp.api.RetrofitHelper
import io.goooler.demoapp.base.BaseViewModel
import io.goooler.demoapp.main.api.MainApi
import io.goooler.demoapp.main.repository.MainRepository
import io.goooler.demoapp.util.LogUtil
import io.goooler.demoapp.util.RequestUtil
import io.goooler.demoapp.util.toJson
import io.reactivex.schedulers.Schedulers
import okhttp3.Response

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val repository by lazy {
        MainRepository(RetrofitHelper.createApiService(MainApi::class.java))
    }

    val title = ObservableField<String>()

    fun initData() {
        repository.getDemoAppInfo()
                .subscribeOn(Schedulers.io())
                .filter{
                    it.status
                }.subscribe({
                    title.set(it.entry!!.name)
                }, {
                    silentThrowable(it)
                }).let {
                    addDisposable(it)
                }
    }
}