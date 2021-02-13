package com.example.instagramkotlinmvvm.model

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("postAccountName")
    val postAccountName: String?,
    @SerializedName("postAccountImageUrl")
    val postAccountImageUrl: String?,
    @SerializedName("postUrl")
    val postUrl: String?,
    @SerializedName("likeCount")
    val likeCount: Int?
) {
    var postUuid: Int = 0
}

data class Auth(
    @SerializedName("accountName")
    val accountName: String?,
    @SerializedName("accountImageUrl")
    val accountImageUrl: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?
) {
    var accountUuid: Int = 0
}