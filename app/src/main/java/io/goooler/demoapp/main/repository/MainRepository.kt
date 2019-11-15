package io.goooler.demoapp.main.repository

import io.goooler.demoapp.api.HttpResponse
import io.goooler.demoapp.main.api.MainApi
import io.goooler.demoapp.main.bean.DemoAppInfoBean
import io.reactivex.Observable

class MainRepository(private val api: MainApi) {

    fun getDemoAppInfo(): Observable<HttpResponse<DemoAppInfoBean>> {
        return api.getDemoAppInfo()
    }
}