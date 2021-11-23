package com.example.webflux.controller

import com.example.webflux.domain.Post
import com.example.webflux.dto.request.PostRequest
import com.example.webflux.dto.response.PostView
import com.example.webflux.service.PostService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

/*
@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun savePost(@RequestBody postRequest: PostRequest): Mono<Post> {
        return postService.createPost(postRequest)
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: String): Mono<PostView> {
        return postService.getById(id)
            .map {
                PostView.of(it)
            }
    }
}*/
