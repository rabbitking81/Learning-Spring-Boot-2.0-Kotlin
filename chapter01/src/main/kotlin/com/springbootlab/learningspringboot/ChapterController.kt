package com.springbootlab.learningspringboot

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class ChapterController(val repository: ChapterRepository) {

    @GetMapping("chapters")
    fun listing(): Flux<Chapter> {
        return repository.findAll()
    }

    @PostMapping("chapters")
    fun addChapter(@RequestParam(required = false, defaultValue = "") name: String): Mono<Chapter> {
        return repository.save(Chapter(name))
    }

    @GetMapping("chapter")
    fun getChapter(@RequestParam(required = false, defaultValue = "") id: String): Mono<Chapter> {
        return repository.findById(id)
    }
}