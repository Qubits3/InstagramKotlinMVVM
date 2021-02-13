package com.example.instagramkotlinmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.instagramkotlinmvvm.model.Post
import com.example.instagramkotlinmvvm.services.PostAPIService
import com.example.instagramkotlinmvvm.util.print
import com.example.instagramkotlinmvvm.util.printError
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()
    private var postAPIService = PostAPIService()

    val posts = MutableLiveData<List<Post>>()

    val list = ArrayList<Post>()

    fun getPosts() {
        list.clear()
        disposable.add(
            postAPIService.getPosts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<JsonObject>() {
                    override fun onNext(database: JsonObject) {
                        database.keySet().forEach { userUID ->
                            val userUIDs = database.get(userUID).asJsonObject
                            userUIDs.keySet().forEach { postUUID -> // Get UUIDs of the posts
                                list.add(Post(
                                    userUIDs.get(postUUID).asJsonObject.get("postAccountName").asString,
                                    userUIDs.get(postUUID).asJsonObject.get("postAccountImageUrl").asString,
                                    userUIDs.get(postUUID).asJsonObject.get("postUrl").asString,
                                    userUIDs.get(postUUID).asJsonObject.get("likeCount").asInt
                                ))
                            }

                            posts.postValue(list)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printError()
                    }

                    override fun onComplete() {

                    }

                })
        )
    }

}