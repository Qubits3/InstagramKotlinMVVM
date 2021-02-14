package com.example.instagramkotlinmvvm.services

import com.example.instagramkotlinmvvm.model.Auth
import com.example.instagramkotlinmvvm.model.Post
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface PostAPI {

    //BASE_URL -> https://instagramkotlinmvvm-default-rtdb.firebaseio.com/

    @PUT("{userUID}/posts/{uuid}.json?auth=9Zr3KR7bYz4tXTxsEATCGq8jDcAdpmVpiR3a7Z9R")
    fun post(
        @Path("userUID") userUID: String,
        @Path("uuid") uuid: String,
        @Body post: Post
    ): Single<JsonObject>

    @GET(".json?auth=9Zr3KR7bYz4tXTxsEATCGq8jDcAdpmVpiR3a7Z9R")
    fun getPosts(): Observable<JsonObject?>

    @PUT("{userUID}/accountInfo.json?auth=9Zr3KR7bYz4tXTxsEATCGq8jDcAdpmVpiR3a7Z9R")
    fun uploadAccountInfo(@Path("userUID") userUID: String, @Body auth: Auth): Single<JsonObject>

    @GET("{userUID}/accountInfo.json?auth=9Zr3KR7bYz4tXTxsEATCGq8jDcAdpmVpiR3a7Z9R")
    fun getAccountInfo(@Path("userUID") userUID: String): Single<JsonObject>

}