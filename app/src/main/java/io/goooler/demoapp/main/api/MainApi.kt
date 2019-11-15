package io.goooler.demoapp.main.api

import io.goooler.demoapp.api.HttpResponse
import io.goooler.demoapp.main.bean.DemoAppInfoBean
import io.reactivex.Observable
import retrofit2.http.GET

interface MainApi {

    @GET("/Goooler/DemoApp/kotlin/app/src/main/assets/demo.json")
    fun getDemoAppInfo(): Observable<HttpResponse<DemoAppInfoBean>>
}