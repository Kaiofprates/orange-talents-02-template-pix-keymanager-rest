package br.orange.zup.register

import br.com.orange.Account
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotNull
@PixValid
@Introspected
class NewPixRequest(
    var key: String?,
    @field:NotNull
    val type: KeyType,
    @field:NotNull
    val account: Account
)


enum class KeyType {
    CPF, PHONE, EMAIL, CNPJ, RANDOM
}
