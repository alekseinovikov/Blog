package me.alekseinovikov.blog.repository

import me.alekseinovikov.blog.domain.post.Post
import me.alekseinovikov.blog.domain.post.PrimaryKey

class PostRepositoryImpl(private val connectionPool: ConnectionPool) : PostRepository {

    override suspend fun findOneById(id: PrimaryKey): Post? =
        connectionPool.querySingle("SELECT id, title, content, created_at FROM posts WHERE id = $1", mapOf("$1" to id)) { row, _ ->
            Post(
                id = row.getLong("id"),
                title = row.getString("title"),
                content = row.getString("content"),
                createdAt = row.getZonedDateTime("created_at")
            )
        }

}
