package com.example.webflux.dto.request

import com.example.webflux.domain.Post
import java.time.LocalDateTime

class PostRequest(
    val userId: String,
    val title: String,
    val content: String
) {
    companion object {
        fun from(postRequest: PostRequest) = Post(
            id = null,
            userId = postRequest.userId,
            title = postRequest.title,
            content = postRequest.content,
            createdAt = LocalDateTime.now(),
            updatedAt = null
        )
    }
}