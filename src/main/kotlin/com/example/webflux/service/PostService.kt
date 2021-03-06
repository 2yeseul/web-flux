package com.example.webflux.service

import com.example.webflux.domain.Post
import com.example.webflux.domain.PostRepository
import com.example.webflux.dto.request.PostRequest
import com.example.webflux.dto.response.PostView
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class PostService(
    private val postRepository: PostRepository
) {
    private val log: Logger = LoggerFactory.getLogger(PostService::class.java)

    fun createPost(request: PostRequest): Mono<Post> {
        val post = Post.of(request)
        return postRepository.save(post)
    }

    fun findAll(): Flux<Post> = postRepository.findAll()

    fun getById(id: String): Mono<Post> = postRepository.findById(id)
        .also {
            log.info("id is $id")
        }
        .switchIfEmpty(
            Mono.error(
                RuntimeException("id $id is not found")
            )
        )

    fun findByUserId(userId: String): Flux<Post> = postRepository.findAllByUserId(userId)
}