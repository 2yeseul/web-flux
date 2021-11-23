package com.example.webflux.dto.response

import com.example.webflux.domain.Post
import java.time.LocalDateTime

data class PostView(
    val id: String,
    val userId: String,
    val title: String,
    val content: String?,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime?
) {
    companion object {
        fun of(post: Post) = PostView(
            id = post.id,
            userId = post.userId,
            title = post.title,
            content = post.content,
            createdAt = post.createdAt,
            updatedAt = post.updatedAt
        )
    }
}
