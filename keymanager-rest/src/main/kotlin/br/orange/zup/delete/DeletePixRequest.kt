package br.orange.zup.delete

import br.orange.zup.handler.validation.IsUUid
import io.micronaut.core.annotation.Introspected
import java.util.*

@Introspected
data class DeletePixRequest(
    @field:IsUUid
    val id: UUID,
)
