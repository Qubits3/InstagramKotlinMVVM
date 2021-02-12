package com.example.instagramkotlinmvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.instagramkotlinmvvm.R
import com.example.instagramkotlinmvvm.databinding.FragmentLoginBinding
import com.example.instagramkotlinmvvm.viewmodel.AuthViewModel
import com.google.gson.JsonParser

class AuthFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var dataBinding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        dataBinding.signUpButton.setOnClickListener {
            signUp(it)
        }

        dataBinding.signInButton.setOnClickListener {
            signIn(it)
        }

        // AUTO SIGN IN
        val payload =
            "{\"email\":\"metin.1464@gmail.com\",\"password\":\"123456\",\"returnSecureToken\":true}"
        val jsonObject = JsonParser().parse(payload).asJsonObject

        authViewModel.signIn(view, jsonObject)
        //

    }

    private fun signUp(view: View) {
        val payload =
            "{email:\"${dataBinding.email.text}\",password:\"${dataBinding.password.text}\",returnSecureToken:true}"
        val jsonObject = JsonParser().parse(payload).asJsonObject

        authViewModel.signUp(view, jsonObject)
    }

    private fun signIn(view: View) {
        val payload =
            "{email:\"${dataBinding.email.text}\",password:\"${dataBinding.password.text}\",returnSecureToken:true}"
        val jsonObject = JsonParser().parse(payload).asJsonObject

        authViewModel.signIn(view, jsonObject)
    }
}