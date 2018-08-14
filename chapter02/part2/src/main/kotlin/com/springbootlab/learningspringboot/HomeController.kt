package com.springbootlab.learningspringboot

import reactor.core.publisher.Mono
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.http.codec.multipart.FilePart
import reactor.core.publisher.Flux
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.http.ResponseEntity
import java.io.IOException
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.GetMapping
import sun.security.x509.OIDMap.addAttribute
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import java.io.InputStream


@Controller
class HomeController(private val imageService: ImageService) {
    companion object {
        private const val BASE_PATH = "/images"
        private const val FILENAME = "{filename:.+}"
    }

    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("mono", imageService.getAllImages())

//        model.addAttribute("mono", Flux.just(
//                Image("1", "learning-spring-boot-cover.jpg"),
//                Image("2", "learning-spring-boot-2nd-edition-cover.jpg"),
//                Image("3", "bazinga.png")
//        ))
        return "index"
    }

    @GetMapping(value = "$BASE_PATH/$FILENAME/raw", produces = [(MediaType.IMAGE_JPEG_VALUE)])
    @ResponseBody
    fun oneRawImage(@PathVariable filename: String): Mono<ResponseEntity<*>> {
        return imageService.findOneImage(filename)
                .map { resource ->
                    try {
                        ResponseEntity.ok().contentLength(resource.contentLength()).body(InputStreamResource(resource.inputStream))
                    } catch (e: IOException) {
                        ResponseEntity.badRequest().body("Couldn't find" + filename + "=>" + e.message)
                    }
                }
    }

    @PostMapping(value = BASE_PATH)
    fun createFile(@RequestPart(name = "file") files: Flux<FilePart>): Mono<String> {
        return imageService.createImage(files)
                .then(Mono.just("redirect:/"))
    }

    @DeleteMapping("$BASE_PATH/$FILENAME")
    fun deleteFile(@PathVariable filename: String): Mono<String> {
        return imageService.deleteImage(filename)
                .then(Mono.just("redirect:/"))
    }
}