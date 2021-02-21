package com.example.instagramkotlinmvvm.services

import com.example.instagramkotlinmvvm.model.Account
import com.example.instagramkotlinmvvm.model.Post
import com.example.instagramkotlinmvvm.util.Util.POST_BASE_URL
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PostAPIService {

    private var api = Retrofit.Builder()
        .baseUrl(POST_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(PostAPI::class.java)

    fun post(userUID: String, uuid: String, post: Post): Single<JsonObject> {
        return api.post(userUID, uuid, post)
    }

    fun getPosts(): Observable<JsonObject?> {
        return api.getPosts()
    }

    fun uploadAccountInfo(userUID: String, account: Account): Single<JsonObject> {
        return api.uploadAccountInfo(userUID, account)
    }

    fun getAccountInfo(userUID: String): Single<JsonObject> {
        return api.getAccountInfo(userUID)
    }

}