package com.example.instagramkotlinmvvm.services

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {

    //WEB API -> AIzaSyCuD6NqXjAcZLDgESUg-pZ0NwlUSfdV8LA
    //BASE_URL -> https://www.googleapis.com/identitytoolkit/v3/
    //EXT -> relyingparty/verifyPassword?key=63eune4Va2Xc2dBVh0S6628XTDEMntdPwavLIVN0

    @POST("relyingparty/signupNewUser?key=AIzaSyCuD6NqXjAcZLDgESUg-pZ0NwlUSfdV8LA")
    fun signUp(@Body payload: JsonObject): Single<JsonObject>

    @POST("relyingparty/verifyPassword?key=AIzaSyCuD6NqXjAcZLDgESUg-pZ0NwlUSfdV8LA")
    fun signIn(@Body payload: JsonObject): Single<JsonObject>

}