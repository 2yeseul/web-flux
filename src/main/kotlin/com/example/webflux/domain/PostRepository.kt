package com.example.webflux.domain

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PostRepository: ReactiveMongoRepository<Post, String> {
    fun findAllByUserId(userId: String): Flux<Post>
}