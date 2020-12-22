package me.alekseinovikov.blog.domain.post

import java.time.ZonedDateTime

data class PostPreview(
    val id: PrimaryKey,
    val title: String,
    val preview: String,
    val createdAt: ZonedDateTime
)
