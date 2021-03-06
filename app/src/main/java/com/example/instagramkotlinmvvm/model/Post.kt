package com.example.instagramkotlinmvvm.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "post")
data class Post(
    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "postUuid")
    @SerializedName("postUuid")
    var postUuid: String,

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
