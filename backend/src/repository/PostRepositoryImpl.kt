package me.alekseinovikov.blog.repository

import me.alekseinovikov.blog.domain.post.Post
import me.alekseinovikov.blog.domain.post.PostPreview
import me.alekseinovikov.blog.domain.post.PrimaryKey
import org.intellij.lang.annotations.Language

class PostRepositoryImpl(private val connectionPool: ConnectionPool) : PostRepository {

    @Language("PostgreSQL")
    override suspend fun findOneById(id: PrimaryKey): Post? =
        connectionPool.querySingle("SELECT id, title, content, created_at FROM posts WHERE id = $1", mapOf("$1" to id), Mappers.post())


    @Language("PostgreSQL")
    override suspend fun getPreviewsOrderByCreationDesc(): List<PostPreview> =
        connectionPool.queryList("SELECT id, title, preview, created_at FROM posts ORDER BY created_at DESC", Mappers.postPreview())

}
