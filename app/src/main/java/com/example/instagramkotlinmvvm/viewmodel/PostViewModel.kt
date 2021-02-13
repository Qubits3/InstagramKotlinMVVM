package com.example.instagramkotlinmvvm.viewmodel

import android.app.Application
import android.net.Uri
import com.example.instagramkotlinmvvm.model.Post
import com.example.instagramkotlinmvvm.services.PostAPIService
import com.example.instagramkotlinmvvm.util.print
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class PostViewModel(application: Application) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()
    private var postAPIService = PostAPIService()

    private lateinit var storageReference: StorageReference
    lateinit var selected: Uri

    fun uploadImage(userUID: String) {

        val uuid = UUID.randomUUID()

        storageReference = FirebaseStorage.getInstance().reference

        val image = "images/$uuid.jpg"

        val newReference = storageReference.child(image)

        newReference.putFile(selected).addOnSuccessListener {
            run {
                val profileImageRef = FirebaseStorage.getInstance().getReference("images/$uuid.jpg")

                profileImageRef.downloadUrl.addOnCompleteListener {
                    post(
                        userUID,
                        Post(
                            "postAccountName",
                            "postAccountImageUrl",
                            it.result.toString(),   // Get image url
                            0
                        )
                    )
                }
            }
        }
    }

    private fun post(userUID: String, post: Post) {
        disposable.add(
            postAPIService.post(userUID, post)
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

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}