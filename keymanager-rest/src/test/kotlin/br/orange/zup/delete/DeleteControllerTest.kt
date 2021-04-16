package br.orange.zup.delete

import br.com.orange.KeymanagerRemoveServiceGrpc
import br.com.orange.RemoveRequest
import br.com.orange.RemoveResponse
import br.orange.zup.httpClientTest.ClientTest
import br.orange.zup.stubs.RegisterGrpcServer
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class DeleteControllerTest{


    @field:Inject
    lateinit var deleteClientStub: KeymanagerRemoveServiceGrpc.KeymanagerRemoveServiceBlockingStub

    @field:Inject
    lateinit var clientDelete: ClientTest

    companion object{
        @JvmStatic
        val CLIENT_ID = UUID.randomUUID()
        @JvmStatic
        val ID = UUID.randomUUID()
        @JvmStatic
        val removeRequest  = RemoveRequest.newBuilder().setId(ID.toString()).setClientId(CLIENT_ID.toString()).build()
        @JvmStatic
        val retornoHttp = RemoveResponse.newBuilder().setMessage("mensagem").build()
    }


    @Test
    fun `deve deletar uma chave pix`(){

        `when`(deleteClientStub.removepix(removeRequest))
            .thenReturn(retornoHttp)

        val body = DeletePixRequest(ID)

        val request = clientDelete.delete(CLIENT_ID,body)

        assertEquals(request.status.code, HttpStatus.OK.code)
        assertEquals(request.body().message, "mensagem")

    }


    @Factory
    @Replaces(factory = RegisterGrpcServer::class)
    internal class MockitoStubFactory{
        @Singleton
        fun stubMock() = Mockito.mock(KeymanagerRemoveServiceGrpc.KeymanagerRemoveServiceBlockingStub::class.java)
    }





}