package me.alekseinovikov.blog.repository

import me.alekseinovikov.blog.domain.post.Post
import me.alekseinovikov.blog.domain.post.PostPreview
import me.alekseinovikov.blog.domain.post.PrimaryKey

interface PostRepository {
    suspend fun findOneById(id: PrimaryKey): Post?
    suspend fun getPreviewsOrderByCreationDesc(): List<PostPreview>
}
