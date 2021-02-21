package com.example.instagramkotlinmvvm.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Account(
    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "userUID")
    @SerializedName("userUID")
    var userUID: String,

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
