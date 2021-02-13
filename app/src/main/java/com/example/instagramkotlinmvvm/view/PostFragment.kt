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
import androidx.navigation.Navigation
import com.example.instagramkotlinmvvm.R
import com.example.instagramkotlinmvvm.databinding.FragmentPostBinding
import com.example.instagramkotlinmvvm.util.printError
import com.example.instagramkotlinmvvm.viewmodel.PostViewModel
import java.io.IOException

class PostFragment : Fragment() {

    private lateinit var dataBinding: FragmentPostBinding
    private lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)

        dataBinding.postButton.setOnClickListener {
            arguments?.let { bundle ->
                val action = PostFragmentDirections.actionPostFragmentToFeedFragment(PostFragmentArgs.fromBundle(bundle).userUIDPost)
                Navigation.findNavController(view).navigate(action)
            }
        }

        dataBinding.postImageView.setOnClickListener {
            selectImage(it)
        }

        dataBinding.postButton.setOnClickListener {
            arguments?.let {
                viewModel.uploadImage(PostFragmentArgs.fromBundle(it).userUIDPost)

                val action = PostFragmentDirections.actionPostFragmentToFeedFragment(PostFragmentArgs.fromBundle(it).userUIDPost)
                Navigation.findNavController(view).navigate(action)
            }
        }
    }

    private fun observeLiveData() {

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
                val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, viewModel.selected)
                dataBinding.postImageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printError()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }



}