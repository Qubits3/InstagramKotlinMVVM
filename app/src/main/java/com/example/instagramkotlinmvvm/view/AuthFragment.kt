package com.example.instagramkotlinmvvm.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.instagramkotlinmvvm.R
import com.example.instagramkotlinmvvm.databinding.FragmentAuthBinding
import com.example.instagramkotlinmvvm.util.printError
import com.example.instagramkotlinmvvm.viewmodel.AuthViewModel
import com.google.gson.JsonParser
import java.io.IOException

class AuthFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var dataBinding: FragmentAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        dataBinding.signUpButton.setOnClickListener {
            signUp(it)
        }

        dataBinding.signInButton.setOnClickListener {
            signIn(it)
        }

        dataBinding.authImageView.setOnClickListener {
            selectImage(it)
        }

        // AUTO SIGN IN
        val payload =
            "{\"email\":\"metin.1464@gmail.com\",\"password\":\"123456\",\"returnSecureToken\":true}"
        val jsonObject = JsonParser().parse(payload).asJsonObject

        viewModel.signIn(view, jsonObject)
        //

    }

    private fun signUp(view: View) {
        val payload =
            "{email:\"${dataBinding.email.text}\",password:\"${dataBinding.password.text}\",returnSecureToken:true}"
        val jsonObject = JsonParser().parse(payload).asJsonObject

        viewModel.signUp(view, jsonObject, dataBinding.loginAccountName.text.toString(), dataBinding.email.text.toString())
    }

    private fun signIn(view: View) {
        val payload =
            "{email:\"${dataBinding.email.text}\",password:\"${dataBinding.password.text}\",returnSecureToken:true}"
        val jsonObject = JsonParser().parse(payload).asJsonObject

        arguments?.let {
            viewModel.signIn(view, jsonObject)
        }
    }

    private fun selectImage(view: View) {
        if (ContextCompat.checkSelfPermission(
                view.context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                view.context as Activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            viewModel.selected = data.data!!
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(context?.contentResolver, viewModel.selected)

                dataBinding.authImageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printError()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

}