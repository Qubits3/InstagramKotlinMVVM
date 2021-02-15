package com.example.instagramkotlinmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramkotlinmvvm.R
import com.example.instagramkotlinmvvm.databinding.ItemPostBinding
import com.example.instagramkotlinmvvm.model.Post

class PostAdapter(private val postList: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(var view: ItemPostBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemPostBinding>(inflater, R.layout.item_post, parent, false)

        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.view.post = postList[position]
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun updatePostList(newPostList: List<Post>) {
        postList.clear()
        postList.addAll(newPostList)

        notifyDataSetChanged()
    }
}