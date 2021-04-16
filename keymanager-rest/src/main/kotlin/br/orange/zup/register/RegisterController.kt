package br.orange.zup.register

import br.com.orange.KeymanagerServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/api/v1/client/{clientId}")
open class RegisterController(@Inject val client: KeymanagerServiceGrpc.KeymanagerServiceBlockingStub) {

    @Post("/pix")
    open fun create(clientId: UUID, @Valid @Body request: NewPixRequest): HttpResponse<CreateResponse>{

        val response = client.register(request.toModel(clientId))
        return HttpResponse.created(CreateResponse(response.id,response.clientId))

    }

}