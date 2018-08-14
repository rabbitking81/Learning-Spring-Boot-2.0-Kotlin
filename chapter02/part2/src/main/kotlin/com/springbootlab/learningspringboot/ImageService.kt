package com.springbootlab.learningspringboot

import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.nio.file.Files
import java.nio.file.Paths
import java.io.FileWriter
import org.springframework.util.FileCopyUtils
import java.io.File
import org.springframework.util.FileSystemUtils
import java.io.IOException
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import java.awt.Image


@Service
class ImageService {
    private val UPLOAD_ROOT = "upload-dir"
    val resourceLoader: ResourceLoader

    constructor(resourceLoader: ResourceLoader) {
        this.resourceLoader = resourceLoader
    }

    fun createImage(files: Flux<FilePart>): Mono<Void> {
        return files.flatMap { it.transferTo(Paths.get(UPLOAD_ROOT, it.filename()).toFile()) }.then()
    }

    fun findOneImage(fileName: String): Mono<Resource> {
        return Mono.fromSupplier { resourceLoader.getResource("file:$UPLOAD_ROOT/$fileName") }
    }

    fun deleteImage(fileName: String): Mono<Void> {
        return Mono.fromRunnable { Files.deleteIfExists(Paths.get(UPLOAD_ROOT, fileName)) }
    }

    fun getAllImages() : Flux<com.springbootlab.learningspringboot.Image> {
        val file = Paths.get(UPLOAD_ROOT).toFile()
        val array = arrayListOf<com.springbootlab.learningspringboot.Image>()
        for(item in file.listFiles()) {
            array.add(Image(file.name.hashCode().toString(), file.name))
        }

        return Flux.fromIterable(array)
    }

    @Bean
    @Throws(IOException::class)
    fun setUp() = CommandLineRunner {
        FileSystemUtils.deleteRecursively(File(UPLOAD_ROOT))

        Files.createDirectory(Paths.get(UPLOAD_ROOT))

        FileCopyUtils.copy("Test file", FileWriter("$UPLOAD_ROOT/test"))

        FileCopyUtils.copy("Test file2", FileWriter("$UPLOAD_ROOT/test2"))

        FileCopyUtils.copy("Test file3", FileWriter("$UPLOAD_ROOT/test3"))
    }
}