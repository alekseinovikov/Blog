package me.alekseinovikov.blog.repository

import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import me.alekseinovikov.blog.domain.post.Post
import me.alekseinovikov.blog.domain.post.PostPreview
import java.time.ZonedDateTime

private fun Row.getNullableLong(columnName: String): Long? = this.get(columnName, java.lang.Long::class.java)?.toLong()
private fun Row.getLong(columnName: String): Long = this.get(columnName, java.lang.Long::class.java)!!.toLong()

private fun Row.getNullableString(columnName: String): String? = this.get(columnName, java.lang.String::class.java)?.toString()
private fun Row.getString(columnName: String): String = this.get(columnName, java.lang.String::class.java)!!.toString()

private fun Row.getNullableZonedDateTime(columnName: String): ZonedDateTime? = this.get(columnName, ZonedDateTime::class.java)
private fun Row.getZonedDateTime(columnName: String): ZonedDateTime = this.get(columnName, ZonedDateTime::class.java)!!


object Mappers {
    fun post() = { row: Row, _: RowMetadata ->
        Post(
            id = row.getLong("id"),
            title = row.getString("title"),
            content = row.getString("content"),
            createdAt = row.getZonedDateTime("created_at")
        )
    }

    fun postPreview() = { row: Row, _: RowMetadata ->
        PostPreview(
            id = row.getLong("id"),
            title = row.getString("title"),
            preview = row.getString("preview"),
            createdAt = row.getZonedDateTime("created_at")
        )
    }
}
