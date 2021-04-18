package br.orange.zup.findKey

import io.micronaut.core.annotation.Introspected

@Introspected
data class FindKeyResponse(

    val id: String,
    val nome: String,
    val cpf: String,
    val agencia: String,
    val instituicao: String,
    val conta: String,
    val tipoDeConta: String,
    val clientId: String,
    val criadoEm: String,
    val chave: String,
    val tipoDeChave: String


)
