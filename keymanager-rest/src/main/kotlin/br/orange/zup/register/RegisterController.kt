package br.orange.zup.register

import br.com.orange.Account
import br.com.orange.KeymanagerServiceGrpc
import br.com.orange.Keytype
import br.com.orange.RegisterRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/api/v1/client/{clientId}")
class RegisterController(@Inject val client: KeymanagerServiceGrpc.KeymanagerServiceBlockingStub) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Post("/pix")
    fun create(clientId: UUID, @Valid @Body request: NewPixRequest): HttpResponse<Any>{
    val response = client.register(RegisterRequest.newBuilder()
        .setType(Keytype.valueOf(request.type.name))
        .setClientId(clientId.toString())
        .setValue(request.key)
        .setAccount(Account.valueOf(request.account.name))
        .build())

    return HttpResponse.created(CreateResponse(response.id,response.clientId))
    }

    data class CreateResponse(val id: String, val clientId: String)


}