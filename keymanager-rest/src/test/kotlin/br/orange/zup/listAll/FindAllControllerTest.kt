package br.orange.zup.listAll

import br.com.orange.*
import br.orange.zup.Utils
import br.orange.zup.httpClientTest.ClientTest
import br.orange.zup.stubs.RegisterGrpcServer
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@MicronautTest
internal class FindAllControllerTest{


    @field:Inject
    lateinit var findKeyStub: KeymanagerFindKeyGrpc.KeymanagerFindKeyBlockingStub


    @field:Inject
    lateinit var getAllKeys: KeymanagerListServiceGrpc.KeymanagerListServiceBlockingStub


    @field:Inject
    lateinit var clientFindAll: ClientTest

    companion object {
        @JvmStatic
        val CLIENT_ID = UUID.randomUUID()
        @JvmStatic
        val ID = UUID.randomUUID()
        @JvmStatic
        val requestFind = FindRequest.newBuilder().setPixId(FindRequest.FiltroPorPixId.newBuilder().setClientId(CLIENT_ID.toString()).setId(
            ID.toString()).build()).build()
        @JvmStatic
        val response = FindResponse.newBuilder()
            .setAgencia("0001")
            .setChave("qualquer chave")
            .setClientId(CLIENT_ID.toString())
            .setConta("12335")
            .setCpf("1234567")
            .setNome("John Doe")
            .setId(ID.toString())
            .setInstituicao("Itaú Unibanco")
            .setTipoDeConta("CORRENTE")
            .setCriadoEm("qualquer data")
            .setTipoDeChave("ALEATORIA")
            .build()

        @JvmStatic
        val listRequest = ListRequest.newBuilder().setClientId(CLIENT_ID.toString()).build()

    }

    @Test
    fun `Deve retornar dados mockados de uma chave`(){

        `when`(findKeyStub.findByKey(requestFind))
            .thenReturn(response)

        val request = clientFindAll.findKey(CLIENT_ID, ID)

        assertEquals(request.status.code,HttpStatus.OK.code)

    }


    @Test
    fun`Deve falhar na busca por chave`(){
        `when`(findKeyStub.findByKey(requestFind))
            .thenReturn(Utils.MockitoHelper.anyObject())

        // FIXME: 19/04/2021  tomo um nullpointer, ainda não sei o porque, ou se é realmente um resultado esperado
        assertThrows<HttpClientResponseException> {
            clientFindAll.findKey(CLIENT_ID, UUID.randomUUID())
        }

    }

    @Test
    fun`Deve retornar uma lista vazia para busca de todas as chaves`(){

        `when`(getAllKeys.listKey(listRequest))
            .thenReturn(ListResponse.getDefaultInstance())

        val request = clientFindAll.getAllKeys(CLIENT_ID)

        assertEquals(request.status.code,HttpStatus.OK.code)

    }


    @Factory
    @Replaces(factory = RegisterGrpcServer::class)
    internal class MokitoStubFactory{
        @Singleton
        fun stubMock() = mock(KeymanagerFindKeyGrpc.KeymanagerFindKeyBlockingStub::class.java)

        @Singleton
        fun findAllKeys() = mock(KeymanagerListServiceGrpc.KeymanagerListServiceBlockingStub::class.java)
    }


}