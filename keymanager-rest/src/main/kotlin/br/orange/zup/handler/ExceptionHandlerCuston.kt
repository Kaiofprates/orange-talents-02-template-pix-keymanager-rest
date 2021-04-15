package br.orange.zup.handler

import br.orange.zup.handler.exceptions.InvalidPixException
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpRequest
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import javax.inject.Singleton


@Produces
@Singleton
@Requires(classes = [ExceptionHandler::class])
class CustonExceptions: ExceptionHandler<StatusRuntimeException?,HttpResponse<Any>> {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun handle(request: HttpRequest<*>?, exception: StatusRuntimeException?): HttpResponse<Any>{

        val code = exception?.status?.code

        when(code){

            Status.INVALID_ARGUMENT.code -> return HttpResponse.badRequest(ErrorResponse(exception?.message.toString(), exception!!.status.code))
            Status.ALREADY_EXISTS.code -> return HttpResponse.unprocessableEntity()
            else -> log.error(" Error: ${exception?.message} - Cause: ${exception?.cause}")
        }

       return HttpResponse.unprocessableEntity()
    }
}

@Introspected
data class ErrorResponse(
    val message: String,
    val status_code: Status.Code,
    val timestamp: String = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date())


)
