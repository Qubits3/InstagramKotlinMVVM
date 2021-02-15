package com.example.instagramkotlinmvvm.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.instagramkotlinmvvm.model.Post

@Dao
interface PostDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPosts(vararg posts: Post)

    @Query("SELECT * FROM post")
    suspend fun getAllPosts(): List<Post>

    @Query("SELECT * FROM post WHERE postUuid = :postId")
    suspend fun getPost(postId: String): Post

    @Query("DELETE FROM post")
    suspend fun deleteAllPosts()
}