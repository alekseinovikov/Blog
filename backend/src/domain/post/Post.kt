package me.alekseinovikov.blog.domain.post

import java.time.ZonedDateTime

data class Post(
    val id: PrimaryKey,
    val title: String,
    val content: String,
    val createdAt: ZonedDateTime
)
