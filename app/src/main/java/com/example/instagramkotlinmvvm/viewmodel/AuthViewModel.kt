package com.example.instagramkotlinmvvm.viewmodel

import android.app.Application
import android.net.Uri
import android.view.View
import androidx.navigation.Navigation
import com.example.instagramkotlinmvvm.model.Account
import com.example.instagramkotlinmvvm.services.AuthAPIService
import com.example.instagramkotlinmvvm.services.PostAPIService
import com.example.instagramkotlinmvvm.util.print
import com.example.instagramkotlinmvvm.util.printError
import com.example.instagramkotlinmvvm.util.showErrorToUser
import com.example.instagramkotlinmvvm.view.AuthFragmentDirections
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.util.*

class AuthViewModel(application: Application) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()
    private val authAPIService = AuthAPIService()
    private val postAPIService = PostAPIService()

    private lateinit var storageReference: StorageReference
    lateinit var selected: Uri
    private var accountImageUrl = ""

    fun signUp(
        view: View,
        payload: JsonObject,
        accountName: String,
        email: String
    ) {

        val uuid = UUID.randomUUID().toString()

        storageReference = FirebaseStorage.getInstance().reference

        val image = "images/$uuid.jpg"

        val newReference =
            storageReference.child(image)    // ToDo: Resim seçilmeyince null dönüyor resim seçilene kadar sign up butonu disable yapılacak

        newReference.putFile(selected).addOnSuccessListener {

            val accountImageRef = FirebaseStorage.getInstance().getReference("images/$uuid.jpg")

            accountImageRef.downloadUrl.addOnCompleteListener {
                accountImageUrl = it.result.toString()
                _signUp(view, payload, accountName, accountImageUrl, email)
            }
        }
    }

    fun signIn(
        view: View,
        payload: JsonObject
    ) {

        disposable.add(
            authAPIService.signIn(payload)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<JsonObject>() {
                    override fun onSuccess(localId: JsonObject) {

                        disposable.add(
                            postAPIService.getAccountInfo(localId.get("localId").asString)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(object : DisposableSingleObserver<JsonObject>() {
                                    override fun onSuccess(accountInfo: JsonObject) {
                                        val action =
                                            AuthFragmentDirections.actionLoginFragmentToFeedFragment(
                                                localId.get("localId").asString,
                                                accountInfo.get("accountName").asString,
                                                accountInfo.get("accountImageUrl").asString
                                            )
                                        Navigation.findNavController(view).navigate(action)
                                    }

                                    override fun onError(e: Throwable) {
                                        e.printStackTrace()
                                    }

                                })
                        )
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        e.showErrorToUser(view.context)
                    }
                })
        )
    }

    private fun uploadAccountInfo(userUID: String, account: Account) {
        disposable.add(
            postAPIService.uploadAccountInfo(userUID, account)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<JsonObject>() {
                    override fun onSuccess(t: JsonObject) {
                        "Account Created!".print()
                    }

                    override fun onError(e: Throwable) {
                        e.printError()
                    }

                })
        )
    }

    private fun _signUp(
        view: View,
        payload: JsonObject,
        accountName: String,
        accountImageUrl: String,
        email: String
    ) {

        disposable.add(
            authAPIService.signUp(payload)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<JsonObject>() {

                    override fun onSuccess(t: JsonObject) {

                        launch {
                            uploadAccountInfo(
                                t.get("localId").asString,
                                Account(t.get("localId").asString, accountName, accountImageUrl, email)
                            )
                        }.invokeOnCompletion {
                            val action =
                                AuthFragmentDirections.actionLoginFragmentToFeedFragment(
                                    t.get("localId").asString,
                                    accountName,
                                    accountImageUrl
                                )
                            Navigation.findNavController(view).navigate(action)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.print()
                        e.showErrorToUser(view.context)
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}