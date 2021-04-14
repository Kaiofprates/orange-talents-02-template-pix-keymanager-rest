package br.orange.zup.register

import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get


@Controller("/api")
class RegisterController {

    @Get("/pix")
    fun hello(): HttpResponse<String>{
        return ok("Ola mundo!")
    }

}