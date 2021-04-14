package br.orange.zup.stubs

import br.com.orange.KeymanagerServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class RegisterGrpcServer {

@Singleton
fun registerClientStub(@GrpcChannel("keymanager") channel: ManagedChannel): KeymanagerServiceGrpc.KeymanagerServiceBlockingStub? {
    return KeymanagerServiceGrpc.newBlockingStub(channel)
}

}

