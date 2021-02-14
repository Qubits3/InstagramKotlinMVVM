package com.example.instagramkotlinmvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Post(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "postUuid")
    @SerializedName("postUuid")
    var postUuid: String?,

    @ColumnInfo(name = "postAccountName")
    @SerializedName("postAccountName")
    val postAccountName: String?,

    @ColumnInfo(name = "postAccountImageUrl")
    @SerializedName("postAccountImageUrl")
    val postAccountImageUrl: String?,

    @ColumnInfo(name = "postUrl")
    @SerializedName("postUrl")
    val postUrl: String?,

    @ColumnInfo(name = "likeCount")
    @SerializedName("likeCount")
    val likeCount: Int?,

    @ColumnInfo(name = "timestamp")
    @SerializedName("timestamp")
    val timestamp: String?
)

@Entity
data class Auth(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userUID")
    @SerializedName("userUID")
    var userUID: String?,

    @ColumnInfo(name = "accountName")
    @SerializedName("accountName")
    val accountName: String?,

    @ColumnInfo(name = "accountImageUrl")
    @SerializedName("accountImageUrl")
    val accountImageUrl: String?,

    @ColumnInfo(name = "email")
    @SerializedName("email")
    val email: String?
)