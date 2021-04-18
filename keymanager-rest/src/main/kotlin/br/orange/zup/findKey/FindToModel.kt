package br.orange.zup.findKey

import br.com.orange.FindResponse

fun  FindResponse.toResponse(): FindKeyResponse {
    return FindKeyResponse(
        id = this.id,
        agencia = this.agencia,
        cpf = this.cpf,
        instituicao = this.instituicao,
        nome = this.nome,
        conta = this.conta,
        tipoDeConta = this.tipoDeConta,
        clientId = this.clientId,
        criadoEm = this.criadoEm,
        chave = this.chave,
        tipoDeChave = this.tipoDeChave
    )
}