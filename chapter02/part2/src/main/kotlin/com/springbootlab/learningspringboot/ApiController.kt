package com.springbootlab.learningspringboot

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api")
class ApiController {
    val log = LoggerFactory.getLogger(ApiController::class.java)!!

    @GetMapping("/images")
    fun images() : Flux<Image> {
        return Flux.just(
                Image("1", "learning-spring-boot-cover.jpg"),
                Image("2", "learning-spring-boot-2nd-edition-cover.jpg"),
                Image("3", "bazinga.png")
        )
    }

    @PostMapping("/images")
    fun create(@RequestBody images: Flux<Image>) : Mono<Void> {
        return images.map {
            log.info("We will save" + it + "to a Reactive database soon!")
            it
        }.then()
    }
}