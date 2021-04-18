package br.orange.zup.listAll

import br.com.orange.KeymanagerListServiceGrpc
import br.com.orange.ListRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.*
import javax.inject.Inject


@Controller("/api/v1/client/{clientId}")
class FindAllController(@Inject val getAll: KeymanagerListServiceGrpc.KeymanagerListServiceBlockingStub) {

    @Get("/keys")
    fun getAllKeys(clientId: UUID): HttpResponse<Any>{
        val response  = getAll.listKey(ListRequest.newBuilder()
            .setClientId(clientId.toString())
            .build())

        return HttpResponse.ok( response.keysList.map {
            it.toResponse()
        })
    }

}