package me.alekseinovikov.blog.service.post

import me.alekseinovikov.blog.domain.post.Post
import me.alekseinovikov.blog.domain.post.PostPreview
import me.alekseinovikov.blog.domain.post.PrimaryKey

interface PostService {
    suspend fun getById(id: PrimaryKey): Post?
    suspend fun getPreviews(): List<PostPreview>
}
