package com.example.instagramkotlinmvvm.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Model(
    @SerializedName("accountInfo")
    val accountInfo: Account,
    @SerializedName("posts")
    val posts: List<Post>
)
