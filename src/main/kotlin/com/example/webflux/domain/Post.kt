package com.example.webflux.domain

import com.example.webflux.dto.request.PostRequest
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(collection = "post")
data class Post(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val title: String,
    val content: String?,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime?
) {
    companion object {
        fun of(postRequest: PostRequest) = Post(
            userId = postRequest.userId,
            title = postRequest.title,
            content = postRequest.content,
            createdAt = LocalDateTime.now(),
            updatedAt = null
        )
    }
}