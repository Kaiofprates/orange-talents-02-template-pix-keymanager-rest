package br.orange.zup.findKey

import br.com.orange.FindRequest
import br.com.orange.FindResponse
import br.com.orange.KeymanagerFindKeyGrpc
import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/api/v1/client/{clientId}")
class FindKeyController(@Inject val client: KeymanagerFindKeyGrpc.KeymanagerFindKeyBlockingStub){


    @Get("/key/{key}")
    fun findKey(clientId: UUID, key: UUID): HttpResponse<FindKeyResponse>{

     val response = client.findByKey(FindRequest.newBuilder()
         .setPixId(
             FindRequest.FiltroPorPixId.newBuilder()
             .setId(key.toString())
             .setClientId(clientId.toString())
             .build())
         .build())

     return HttpResponse.ok(response.toResponse())
    }

}

