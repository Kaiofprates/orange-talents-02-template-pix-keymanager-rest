package br.orange.zup.register

import br.com.orange.Account
import br.com.orange.Keytype
import br.com.orange.RegisterRequest
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotNull

@PixValid
@Introspected
data class NewPixRequest(
    val key: String,
    @field:NotNull
    val type: KeyType,
    @field:NotNull
    val account: Account
){
    fun toModel(clientId: UUID): RegisterRequest {
        return RegisterRequest.newBuilder()
            .setType(Keytype.valueOf(this.type.name))
            .setClientId(clientId.toString())
            .setValue(this.key)
            .setAccount(Account.valueOf(this.account.name))
            .build()
    }
}

