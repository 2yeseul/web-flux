package com.example.webflux.router

import com.example.webflux.dto.request.PostRequest
import com.example.webflux.dto.response.PostView
import com.example.webflux.service.PostService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.notFound
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono
import java.util.*

@Configuration
class PostRouter(
    private val postHandler: PostHandler
) {
    @Bean
    fun routers() = nest(path("/posts"),
        router {
            listOf(
                GET("/"),
                GET("/{id}"),
                POST("/", postHandler::save)
            )
        }
    )
}

@Component
class PostHandler(
    private val postService: PostService
) {

    fun save(@RequestBody request: ServerRequest): Mono<ServerResponse> = ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(request.bodyToMono(PostRequest::class.java)
            .switchIfEmpty(Mono.empty())
            .filter(Objects::nonNull)
            .map { post ->
                Mono.fromCallable {
                    postService.createPost(post)
                }.then(Mono.just(post))
            }
        ).switchIfEmpty(notFound().build())
}
