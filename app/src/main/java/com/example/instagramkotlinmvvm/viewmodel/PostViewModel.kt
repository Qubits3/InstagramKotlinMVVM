package com.example.instagramkotlinmvvm.viewmodel

import android.app.Application
import android.net.Uri
import com.example.instagramkotlinmvvm.model.Model
import com.example.instagramkotlinmvvm.model.Post
import com.example.instagramkotlinmvvm.services.ModelAPIService
import com.example.instagramkotlinmvvm.services.PostAPIService
import com.example.instagramkotlinmvvm.util.print
import com.example.instagramkotlinmvvm.util.printError
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*

class PostViewModel(application: Application) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()
    private var postAPIService = PostAPIService()
    private var modelAPIService = ModelAPIService()

    private lateinit var storageReference: StorageReference
    lateinit var selected: Uri

    fun uploadImage(userUID: String, postAccountName: String, postAccountImageUrl: String) {

        val uuid = UUID.randomUUID().toString()

        storageReference = FirebaseStorage.getInstance().reference

        val image = "images/$uuid.jpg"

        val newReference = storageReference.child(image)

        newReference.putFile(selected).addOnSuccessListener {
            run {
                val profileImageRef = FirebaseStorage.getInstance().getReference("images/$uuid.jpg")

                profileImageRef.downloadUrl.addOnCompleteListener {
                    post(
                        userUID,
                        uuid,
                        Post(
                            uuid,
                            postAccountName,
                            postAccountImageUrl,
                            it.result.toString(),   // Get image url
                            0,
                            System.nanoTime().toString()
                        )
                    )
                }
            }
        }
    }

    private fun post(userUID: String, uuid: String, post: Post) {
        disposable.add(
            postAPIService.post(userUID, uuid, post)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<JsonObject>() {

                    override fun onSuccess(t: JsonObject) {
                        print("onSuccess")
                    }

                    override fun onError(e: Throwable) {
                        e.print()
                    }
                })
        )
    }

    fun getDatabase() {
        launch {
            disposable.add(
                modelAPIService.getDatabase()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<List<Model>>() {
                        override fun onNext(t: List<Model>) {
                            t.print("getDatabase: ")
                        }

                        override fun onError(e: Throwable) {
                            e.printError()
                        }

                        override fun onComplete() {
                            "PostViewModel.onComplete()".print()
                        }

                    })
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}