package com.example.instagramkotlinmvvm.services

import com.example.instagramkotlinmvvm.util.Util.AUTH_KEY
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthAPI {

    // POST_BASE_URL -> https://instagramkotlinmvvm-default-rtdb.firebaseio.com/

    // WEB API -> AIzaSyCuD6NqXjAcZLDgESUg-pZ0NwlUSfdV8LA
    // AUTH_BASE_URL -> https://www.googleapis.com/identitytoolkit/v3/
    // EXT -> relyingparty/verifyPassword?key=63eune4Va2Xc2dBVh0S6628XTDEMntdPwavLIVN0

    @POST("relyingparty/signupNewUser")
    fun signUp(
        @Body payload: JsonObject,
        @Query("key") authKey: String = AUTH_KEY
    ): Single<JsonObject>

    @POST("relyingparty/verifyPassword")
    fun signIn(
        @Body payload: JsonObject,
        @Query("key") authKey: String = AUTH_KEY
    ): Single<JsonObject>

}