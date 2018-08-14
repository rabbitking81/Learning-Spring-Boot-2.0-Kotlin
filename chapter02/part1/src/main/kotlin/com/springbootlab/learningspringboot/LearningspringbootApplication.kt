package com.springbootlab.learningspringboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter

@SpringBootApplication
class LearningspringbootApplication {
    @Bean
    fun hiddenHttpMethodFilter(): HiddenHttpMethodFilter {
        return HiddenHttpMethodFilter()
    }
}

fun main(args: Array<String>) {
    runApplication<LearningspringbootApplication>(*args)
}
