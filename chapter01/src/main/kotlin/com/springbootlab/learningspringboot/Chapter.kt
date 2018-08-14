package com.springbootlab.learningspringboot

import org.springframework.data.annotation.Id

data class Chapter(val name: String) {

    @Id
    val id: String? = null

}
