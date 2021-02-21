package com.example.instagramkotlinmvvm.services

import com.example.instagramkotlinmvvm.model.Model
import com.example.instagramkotlinmvvm.util.Util
import retrofit2.Response
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.Query

interface ModelAPI {

    @GET(".json")
    fun getDatabase(@Query("auth") postKey: String = Util.POST_KEY): Observable<List<Model>>

}