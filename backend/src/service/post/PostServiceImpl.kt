package me.alekseinovikov.blog.service.post

import me.alekseinovikov.blog.domain.post.PostPreview
import me.alekseinovikov.blog.domain.post.PrimaryKey
import me.alekseinovikov.blog.repository.PostRepository

class PostServiceImpl(private val postRepository: PostRepository) : PostService {
    override suspend fun getById(id: PrimaryKey) = postRepository.findOneById(id)
    override suspend fun getPreviews(): List<PostPreview> = postRepository.getPreviewsOrderByCreationDesc()
}
