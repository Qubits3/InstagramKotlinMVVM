package com.example.instagramkotlinmvvm.services

import com.example.instagramkotlinmvvm.model.Post
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostAPI {

    //BASE_URL -> https://instagramkotlinmvvm-default-rtdb.firebaseio.com/

    @POST("{userUID}.json?auth=9Zr3KR7bYz4tXTxsEATCGq8jDcAdpmVpiR3a7Z9R")
    fun post(@Path("userUID") userUID: String, @Body post: Post): Single<JsonObject>

    @GET(".json?auth=9Zr3KR7bYz4tXTxsEATCGq8jDcAdpmVpiR3a7Z9R")
    fun getPosts(): Observable<JsonObject>

}