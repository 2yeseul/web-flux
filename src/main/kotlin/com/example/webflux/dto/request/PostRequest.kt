package com.example.webflux.dto.request

import com.example.webflux.domain.Post
import java.time.LocalDateTime
import java.util.*

data class PostRequest(
    val userId: String,
    val title: String,
    val content: String
)