package br.orange.zup.register

import br.com.orange.*
import br.orange.zup.stubs.RegisterGrpcServer
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Replace
import org.mockito.BDDMockito.given
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Valid

@MicronautTest
internal class RegisterControllerTest {


    @field:Inject
    lateinit var registraStub: KeymanagerServiceGrpc.KeymanagerServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    companion object{
        val CLIENT_ID  = UUID.randomUUID().toString()
    }

    // FIXME: 16/04/2021  esse teste não está nada coerente por hora vou mudar a lógica de validação para conseguir fazer testes unitários

    @Test
    fun `deve retornar erro ao passar chave invalida para tipo randomico`(){

      `when`(registraStub.register(RegisterRequest.newBuilder()
            .setAccount(Account.valueOf(Account.SAVINGS.name))
            .setValue("")
            .setClientId(CLIENT_ID)
            .setType(Keytype.valueOf(KeyType.RANDOM.name)).build()))
            .thenReturn(RegisterResponse.newBuilder()
                .setClientId(UUID.randomUUID().toString())
                .setId(UUID.randomUUID().toString())
                .build())


        val novaChavePix = NewPixRequest("",KeyType.RANDOM,Account.SAVINGS)
        val request = HttpRequest.POST("/api/v1/client/$CLIENT_ID/pix", novaChavePix)

         assertThrows<HttpClientResponseException>{
             client.toBlocking().exchange(request,NewPixRequest::class.java)
         }.also {
             assertEquals(it.status.code, 400)
         }

    }


    @Factory
    @Replaces(factory = RegisterGrpcServer::class)
    internal class MockitoStubFactory {
        @Singleton
        fun stubMock() = Mockito.mock(KeymanagerServiceGrpc.KeymanagerServiceBlockingStub::class.java)
    }



}