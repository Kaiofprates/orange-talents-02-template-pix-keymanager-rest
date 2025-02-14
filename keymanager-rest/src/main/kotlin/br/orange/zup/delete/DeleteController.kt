package br.orange.zup.delete

import br.com.orange.KeymanagerRemoveServiceGrpc
import br.com.orange.RemoveRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/api/v1/client/{clientId}")
open class DeleteController(@Inject val client: KeymanagerRemoveServiceGrpc.KeymanagerRemoveServiceBlockingStub) {


    @Delete("/pix")
    open fun delete(clientId: UUID, @Valid @Body request: DeletePixRequest): HttpResponse<DeleteResponse>{

        val response = client.removepix(RemoveRequest.newBuilder()
            .setClientId(clientId.toString())
            .setId(request.idpix.toString()).build())

        if(response.message.isNullOrBlank()) return HttpResponse.unprocessableEntity()

        return HttpResponse.ok(DeleteResponse(response.message))
    }

}

data class DeleteResponse(val message: String)


