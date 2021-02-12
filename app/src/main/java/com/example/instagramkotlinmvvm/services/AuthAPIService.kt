package com.example.instagramkotlinmvvm.services

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AuthAPIService {

    //BASE URL -> https://www.googleapis.com/identitytoolkit/v3/

    private var BASE_URL = "https://www.googleapis.com/identitytoolkit/v3/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AuthAPI::class.java)

    fun signUp(payload: JsonObject): Single<JsonObject> {
        return api.signUp(payload)
    }

    fun signIn(payload: JsonObject): Single<JsonObject> {
        return api.signIn(payload)
    }

}