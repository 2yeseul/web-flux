package com.example.webflux.practice

import kotlinx.coroutines.reactor.flux
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.*

fun main() {
    val seq1: Flux<String> = Flux.just("foo", "bar", "foobar")
    val iterable: List<String> = listOf("foo", "bar", "foobar")
    val seq2: Flux<String> = Flux.fromIterable(iterable)

    val noData: Mono<String> = Mono.empty()
    val data: Mono<String> = Mono.just("foo")

    val ints: Flux<Int> = Flux.range(1, 3)
    ints.subscribe()

    seq1.subscribe {
        println(it)
    }

    Flux.range(1, 4)
        .map {
            if (it <= 3) {
                it
            } else {
                throw RuntimeException("Got to 4")
            }
        }.subscribe {
            println(it)
        }

    "kotlin extension!".toMono()
        .subscribe{
            println(it)
        }

    listOf(1, 2, 3).toFlux()
        .map {
            it * it
        }.subscribe {
            println(it)
        }


}