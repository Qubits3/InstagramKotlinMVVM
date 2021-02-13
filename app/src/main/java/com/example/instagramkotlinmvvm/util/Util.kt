package com.example.instagramkotlinmvvm.util

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.instagramkotlinmvvm.R
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.HttpException

fun Any?.print(prefix: String = ""): Any? {
    println("$prefix $this")
    return this
}

fun Throwable.printError() {
    println(this.localizedMessage)
}

fun Throwable.showErrorToUser(context: Context) {
    val exception = this as HttpException

    val error = Gson().fromJson(
        Gson().fromJson(
            exception.response()?.errorBody()?.string(),
            JsonObject::class.java
        ).get("error"), JsonObject::class.java
    ).get("message").toString()

    println(error)

    when (error) {
        "\"INVALID_EMAIL\"" -> {
            showToastMessage(context, "Invalid Email!")
        }

        "\"INVALID_PASSWORD\"" -> {
            showToastMessage(context, "Invalid Password!")
        }

        "\"EMAIL_NOT_FOUND\"" -> {
            showToastMessage(context, "E-Mail not found!")
        }

        "\"WEAK_PASSWORD : Password should be at least 6 characters\"" -> {
            showToastMessage(context, "Weak password: Password should be at least 6 characters")
        }

        "\"EMAIL_EXISTS\"" -> {
            showToastMessage(context, "E-Mail already taken!")
        }

        "\"TOO_MANY_ATTEMPTS_TRY_LATER : Too many unsuccessful login attempts. Please try again later.\"" -> {
            showToastMessage(
                context,
                "Too many unsuccessful login attempts. Please try again later"
            )
        }

        else -> {
            showToastMessage(context, "Unexpected error occurred!")
        }
    }
}

fun ImageView.downloadFromUrl(url: String, progressDrawable: CircularProgressDrawable) {

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_launcher_foreground)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}

fun placeholderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView, url: String) {
    view.downloadFromUrl(url, placeholderProgressBar(view.context))
}

private fun showToastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}