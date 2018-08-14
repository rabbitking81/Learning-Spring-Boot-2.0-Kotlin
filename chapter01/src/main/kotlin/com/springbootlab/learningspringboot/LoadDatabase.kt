package com.springbootlab.learningspringboot

import reactor.core.publisher.Flux
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class LoadDatabase {
    @Bean
    fun init(repository: ChapterRepository) = CommandLineRunner {
        Flux.just(Chapter( "Quick Start with Java"), Chapter("Reactive Web with Spring Boot"), Chapter( "...and more!"))
                .flatMap { repository.save(it) }
                .subscribe {
                    print(it)
                }
    }
}