package com.example.webflux.router

import com.example.webflux.domain.Post
import com.example.webflux.domain.PostRepository
import com.example.webflux.dto.request.PostRequest
import com.example.webflux.dto.response.PostView
import com.example.webflux.service.PostService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.ServerResponse.notFound
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import java.util.*

@Configuration
@EnableWebFlux
class PostRouter(
    private val postHandler: PostHandler
) {
    @Bean
    fun routers() = nest(path("/posts"),
        router {
            listOf(
                GET("/"),
                GET("/{id}", postHandler::getById),
                POST("", accept(MediaType.APPLICATION_JSON), postHandler::save)
            )
        }
    )
}

@Component
class PostHandler(
    private val postService: PostService,
) {
    private val log: Logger = LoggerFactory.getLogger(PostHandler::class.java)

    fun save(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(PostRequest::class.java)
            .flatMap { postService.createPost(it) }
            .flatMap { ok().bodyValue(PostView.of(it)) }

    fun getById(request: ServerRequest): Mono<ServerResponse> {
        val post = postService.getById(request.pathVariable("id"))
        return post.flatMap { ok().bodyValue(PostView.of(it)) }
    }
}
