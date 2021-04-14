package br.orange.zup.handler

import br.orange.zup.register.InvalidTestException
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import javax.inject.Singleton


@Produces
@Singleton
@Requires(classes = [InvalidTestException::class,ExceptionHandler::class])
class CustonExceptions: ExceptionHandler<InvalidTestException,HttpResponse<Any>> {

    override fun handle(request: HttpRequest<*>?, exception: InvalidTestException?): HttpResponse<Any>{
        return HttpResponse.badRequest("teste de bad request")

    }
}