package br.orange.zup.stubs

import br.com.orange.KeymanagerFindKeyGrpc
import br.com.orange.KeymanagerRemoveServiceGrpc
import br.com.orange.KeymanagerServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class RegisterGrpcServer(@GrpcChannel("keymanager") val channel: ManagedChannel) {

        @Singleton
        fun registerClientStub() = KeymanagerServiceGrpc.newBlockingStub(channel)

        @Singleton
        fun findClientStub() = KeymanagerFindKeyGrpc.newBlockingStub(channel)

        @Singleton
        fun deleteClientStub() = KeymanagerRemoveServiceGrpc.newBlockingStub(channel)

}

