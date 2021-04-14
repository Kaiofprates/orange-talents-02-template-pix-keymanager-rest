package br.orange.zup.register

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
data class NewPixRequest(
    @field:NotBlank
    @field:Size(max = 77)
    val value: String,
    @field:NotNull
    val type: KeyType,
    @field:NotNull
    val account: AccountType
)


enum class KeyType {
    CPF, PHONE, EMAIL, CNPJ, RANDOM
}

enum class AccountType {
    CHECKING, SAVING
}