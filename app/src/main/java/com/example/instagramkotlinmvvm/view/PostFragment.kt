package com.example.instagramkotlinmvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.instagramkotlinmvvm.R
import com.example.instagramkotlinmvvm.databinding.FragmentPostBinding

class PostFragment : Fragment() {

    private lateinit var dataBinding: FragmentPostBinding

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

        dataBinding.postButton.setOnClickListener {
            val action = PostFragmentDirections.actionPostFragmentToFeedFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

}