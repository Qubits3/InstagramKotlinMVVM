package com.example.instagramkotlinmvvm.viewmodel

import android.app.Application
import android.view.View
import androidx.navigation.Navigation
import com.example.instagramkotlinmvvm.services.AuthAPIService
import com.example.instagramkotlinmvvm.util.showErrorToUser
import com.example.instagramkotlinmvvm.view.AuthFragmentDirections
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class AuthViewModel(application: Application) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()
    private val authAPIService = AuthAPIService()

    fun signUp(view: View, payload: JsonObject) {

        disposable.add(
            authAPIService.signUp(payload)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<JsonObject>() {
                    override fun onSuccess(t: JsonObject) {
                        val action = AuthFragmentDirections.actionLoginFragmentToFeedFragment()
                        Navigation.findNavController(view).navigate(action)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        e.showErrorToUser(view.context)
                    }
                })
        )
    }

    fun signIn(view: View, payload: JsonObject) {

        disposable.add(
            authAPIService.signIn(payload)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<JsonObject>() {
                    override fun onSuccess(t: JsonObject) {
                        val action = AuthFragmentDirections.actionLoginFragmentToFeedFragment()
                        Navigation.findNavController(view).navigate(action)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
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