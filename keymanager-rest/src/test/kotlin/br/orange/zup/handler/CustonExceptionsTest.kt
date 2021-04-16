package br.orange.zup.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


@MicronautTest
internal class CustonExceptionsTest {

    val clientGeneric = HttpRequest.GET<Any>("/")


    @Test
    fun `deve retornar Bad Request no caso de argumento inválido`(){

        val invalid_argument = StatusRuntimeException(Status.INVALID_ARGUMENT)

        val response = CustonExceptions().handle(clientGeneric,invalid_argument)

        assertEquals(HttpStatus.BAD_REQUEST, response.status)
    }

    @Test
    fun `deve retornar Unprocessable_entity em caso de chave existente`(){

        val invalid_argument = StatusRuntimeException(Status.ALREADY_EXISTS)

        val response = CustonExceptions().handle(clientGeneric,invalid_argument)

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.status)
    }

    @Test
    fun `deve retornar erro no serviço`(){

        val invalid_argument = StatusRuntimeException(Status.UNKNOWN)

        val response = CustonExceptions().handle(clientGeneric,invalid_argument)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.status)

    }




}