package com.example.instagramkotlinmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.instagramkotlinmvvm.model.Post

class FeedViewModel(application: Application) : BaseViewModel(application) {

    val posts = MutableLiveData<List<Post>>()

    fun postImage() {
        val post = Post("post", "url","url", 50)
        val post2 = Post("post2", "url","url", 50)
        val post3 = Post("post3", "url","url", 50)

        posts.value = arrayListOf(post, post2, post3)
    }

}