package br.orange.zup.listAll

import br.com.orange.ListResponse

fun ListResponse.AllPix.toResponse(): ListPixDetails {

    return ListPixDetails(
        id = this.id,
        key = this.key.toString(),
        pix = this.pix,
        accountType = this.accountType.toString(),
        createAt = this.createAt
    )

}

data class ListPixDetails(
    val id: String,
    val key: String,
    val pix: String,
    val accountType: String,
    val createAt: String
)
