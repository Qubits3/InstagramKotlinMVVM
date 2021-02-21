package com.example.instagramkotlinmvvm.services

import com.example.instagramkotlinmvvm.model.Model
import com.example.instagramkotlinmvvm.util.Util
import com.example.instagramkotlinmvvm.util.Util.POST_BASE_URL
import com.google.gson.Gson
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ModelAPIService {

    private var api = Retrofit.Builder()
        .baseUrl(POST_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ModelAPI::class.java)

    fun getDatabase(): Observable<List<Model>> {
        return api.getDatabase(Util.POST_KEY)
    }

}