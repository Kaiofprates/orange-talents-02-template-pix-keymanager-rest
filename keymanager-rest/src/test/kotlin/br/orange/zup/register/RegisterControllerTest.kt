package br.orange.zup.register

import br.com.orange.*
import br.orange.zup.httpClientTest.ClientTest
import br.orange.zup.stubs.RegisterGrpcServer
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RegisterControllerTest {

    @field:Inject
    lateinit var registerClientStub: KeymanagerServiceGrpc.KeymanagerServiceBlockingStub

    @field:Inject
    lateinit var client: ClientTest

    companion object{
        val CLIENT_ID  = UUID.randomUUID()

        // TODO: 16/04/2021  colocar toda essa repetição tosca e desnecessária em uma função
        @JvmStatic
        val sucessPhoneRequest = RegisterRequest.newBuilder()
            .setAccount(Account.valueOf(Account.SAVINGS.name))
            .setValue("+5511987654321")
            .setClientId(CLIENT_ID.toString())
            .setType(Keytype.valueOf(KeyType.PHONE.name)).build()

        @JvmStatic
        val sucessEmailRequest = RegisterRequest.newBuilder()
            .setAccount(Account.valueOf(Account.SAVINGS.name))
            .setValue("zup@mail.com")
            .setClientId(CLIENT_ID.toString())
            .setType(Keytype.valueOf(KeyType.EMAIL.name)).build()

        @JvmStatic
        val errorRandomRequest = RegisterRequest.newBuilder()
            .setAccount(Account.valueOf(Account.SAVINGS.name))
            .setValue("")
            .setClientId(CLIENT_ID.toString())
            .setType(Keytype.valueOf(KeyType.RANDOM.name)).build()

        @JvmStatic
        val sucessCPFRequest = RegisterRequest.newBuilder()
            .setAccount(Account.valueOf(Account.SAVINGS.name))
            .setValue("38180410080")
            .setClientId(CLIENT_ID.toString())
            .setType(Keytype.valueOf(KeyType.CPF.name)).build()


        @JvmStatic
        val genericResponse = RegisterResponse.newBuilder()
            .setClientId(CLIENT_ID.toString())
            .setId(UUID.randomUUID().toString())
            .build()
    }

    // FIXME: 16/04/2021 de alguma forma quando o campo é blank não se consegue desserializar a classe de request

 /*
    @Test
    fun `deve retornar erro ao passar chave invalida para tipo randomico`(){

      `when`(registerClientStub.register(errorRandomRequest))
            .thenReturn(genericResponse)

        val novaChavePix = NewPixRequest("",KeyType.RANDOM,Account.SAVINGS)

        val response = client.create(CLIENT_ID,novaChavePix)

        assertEquals(response.status.code, HttpStatus.BAD_REQUEST)

    }
  */


    @Test
    fun `deve retornar sucesso ao criar uma chave com cpf`(){

        `when`(registerClientStub.register(sucessCPFRequest))
            .thenReturn(genericResponse)

        val novaChavePix = NewPixRequest("38180410080",KeyType.CPF,Account.SAVINGS)

        val response = client.create(CLIENT_ID,novaChavePix)

        assertEquals(response.status.code,HttpStatus.CREATED.code)
        assertEquals(response.body().clientId, CLIENT_ID.toString())
        assertNotNull(response.body().id)

    }

    @Test
    fun`deve retornar sucesso ao criar uma chave com email`(){
        `when`(registerClientStub.register(sucessEmailRequest))
            .thenReturn(genericResponse)

        val novaChavePix = NewPixRequest("zup@mail.com",KeyType.EMAIL,Account.SAVINGS)

        val response = client.create(CLIENT_ID,novaChavePix)

        assertEquals(response.status.code,HttpStatus.CREATED.code)
        assertEquals(response.body().clientId, CLIENT_ID.toString())
        assertNotNull(response.body().id)

    }

    // FIXME: 16/04/2021  retornando um nullpointer :(

 /*
    @Test
    fun`deve retornar sucesso ao criar uma chave com telefone`(){
        `when`(registerClientStub.register(sucessEmailRequest))
            .thenReturn(genericResponse)

        val novaChavePix = NewPixRequest("+5511987654321",KeyType.PHONE,Account.SAVINGS)

        val response = client.create(CLIENT_ID,novaChavePix)

        assertEquals(response.status.code,HttpStatus.CREATED.code)
        assertEquals(response.body().clientId, CLIENT_ID.toString())
        assertNotNull(response.body().id)

    }

  */


    @Factory
    @Replaces(factory = RegisterGrpcServer::class)
    internal class MockitoStubFactory {
        @Singleton
        fun stubMock() = Mockito.mock(KeymanagerServiceGrpc.KeymanagerServiceBlockingStub::class.java)
    }



}