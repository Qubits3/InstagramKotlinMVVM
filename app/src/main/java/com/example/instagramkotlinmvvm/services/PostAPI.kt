package com.example.instagramkotlinmvvm.services

import com.example.instagramkotlinmvvm.model.Account
import com.example.instagramkotlinmvvm.model.Post
import com.example.instagramkotlinmvvm.util.Util.POST_KEY
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface PostAPI {

    @PUT("{userUID}/posts/{uuid}.json")
    fun post(
        @Path("userUID") userUID: String,
        @Path("uuid") uuid: String,
        @Body post: Post,
        @Query("auth") postKey: String = POST_KEY
    ): Single<JsonObject>

    @GET(".json")
    fun getPosts(@Query("auth") postKey: String = POST_KEY): Observable<JsonObject?>

    @PUT("{userUID}/accountInfo.json")
    fun uploadAccountInfo(
        @Path("userUID") userUID: String,
        @Body account: Account,
        @Query("auth") postKey: String = POST_KEY
    ): Single<JsonObject>

    @GET("{userUID}/accountInfo.json")
    fun getAccountInfo(
        @Path("userUID") userUID: String,
        @Query("auth") postKey: String = POST_KEY
    ): Single<JsonObject>

}