package com.example.instagramkotlinmvvm.view

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramkotlinmvvm.R
import com.example.instagramkotlinmvvm.adapter.PostAdapter
import com.example.instagramkotlinmvvm.databinding.FragmentFeedBinding
import com.example.instagramkotlinmvvm.util.print
import com.example.instagramkotlinmvvm.viewmodel.AuthViewModel
import com.example.instagramkotlinmvvm.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {

    private lateinit var feedViewModel: FeedViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var dataBinding: FragmentFeedBinding
    private val postAdapter = PostAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.feed_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.feed_menu_post -> {
                val action = FeedFragmentDirections.actionFeedFragmentToPostFragment()
                view?.let { Navigation.findNavController(it).navigate(action) }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        postList.layoutManager = LinearLayoutManager(context)
        postList.adapter = postAdapter

        feedViewModel.postImage()

        observeLiveData()
    }

    private fun observeLiveData() {
        feedViewModel.posts.observe(viewLifecycleOwner, { posts ->

            posts?.let {
                postAdapter.updatePostList(posts)
            }
        })
    }
}