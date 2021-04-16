package br.orange.zup.httpClientTest

import br.orange.zup.delete.DeletePixRequest
import br.orange.zup.delete.DeleteResponse
import br.orange.zup.register.CreateResponse
import br.orange.zup.register.NewPixRequest
import br.orange.zup.register.RegisterController
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import java.util.*

@Client("http://localhost:8080/")
interface ClientTest {

    /**
     * por algum motivo eu não consigo usar com sucesso o outro client para testes
     */

    @Delete("api/v1/client/{clientId}/pix")
    fun delete(clientId: UUID, @Body request: DeletePixRequest): HttpResponse<DeleteResponse>

    @Post("api/v1/client/{clientId}/pix")
    fun create(clientId: UUID, @Body request: NewPixRequest): HttpResponse<CreateResponse>



}