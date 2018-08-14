package com.springbootlab.learningspringboot

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ChapterRepository : ReactiveCrudRepository<Chapter, String>