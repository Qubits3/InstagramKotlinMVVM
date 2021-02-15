package com.example.instagramkotlinmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.instagramkotlinmvvm.model.Post
import com.example.instagramkotlinmvvm.services.PostAPIService
import com.example.instagramkotlinmvvm.services.PostDatabase
import com.example.instagramkotlinmvvm.util.printError
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()
    private var postAPIService = PostAPIService()
    private val postDAO = PostDatabase(getApplication()).postDao()

    val posts = MutableLiveData<List<Post>>()
    val isDownloading = MutableLiveData<Boolean>()

    fun refreshFromAPI() {
        isDownloading.postValue(true)
        getPostsFromFirebase()
    }

    private fun getPostsFromFirebase() {
        val list = ArrayList<Post>()
        list.clear()
        disposable.add(
            postAPIService.getPosts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<JsonObject>() {
                    override fun onNext(database: JsonObject) {
                        database.keySet()
                            .forEach { userUID ->                              //all database
                                val accountInfoAndPosts =
                                    database.get(userUID).asJsonObject    //userUID children
                                val postsDB =
                                    accountInfoAndPosts.get("posts").asJsonObject    //posts children
                                postsDB?.let {
                                    postsDB.keySet().forEach { postUUID -> // Get UUIDs of the posts
                                        val post = postsDB.get(postUUID).asJsonObject
                                        list.add(
                                            Post(
                                                post.get("postUuid").asString,
                                                post.get("postAccountName").asString,
                                                post.get("postAccountImageUrl").asString,
                                                post.get("postUrl").asString,
                                                post.get("likeCount").asInt,
                                                post.get("timestamp").asString
                                            )
                                        )
                                    }
                                }
                            }
                    }

                    override fun onError(e: Throwable) {
                        e.printError()
                    }

                    override fun onComplete() {
                        isDownloading.postValue(false)
                        storeInSQLite(list)
                    }
                })
        )
    }

    private fun storeInSQLite(list: List<Post>) {
        launch {
            postDAO.deleteAllPosts()
            postDAO.insertAllPosts(*list.toTypedArray())
        }.invokeOnCompletion {
            getFromSQLite()
        }
    }

    fun getFromSQLite() {
        launch {
            posts.postValue(postDAO.getAllPosts())
        }
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }
}