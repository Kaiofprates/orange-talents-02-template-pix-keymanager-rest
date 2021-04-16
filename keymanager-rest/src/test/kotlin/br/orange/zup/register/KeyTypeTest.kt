package br.orange.zup.register

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@MicronautTest
internal class KeyTypeTest{

    @Test
    fun `passa para chave nula ou vazia`(){

        val keytype = KeyType.RANDOM

        assertTrue(keytype.valida(null))
        assertTrue(keytype.valida(""))
    }

    @Test
    fun `deve falhar para chave aleatoria com valor`(){
        val keytype = KeyType.RANDOM
        assertFalse(keytype.valida("O mundo é um moínho"))
    }

    @Test
    fun `deve passar com numero de cpf valido`(){

        val keytype = KeyType.CPF

        assertTrue(keytype.valida("54092275064"))

    }


    @Test
    fun `deve falhar em caso de cpf inválido`(){

        val keytype = KeyType.CPF

        assertFalse(keytype.valida("54092275062"))
        assertFalse(keytype.valida("null"))
        assertFalse(keytype.valida(""))

    }

    @Test
    fun `deve passar em caso de celular valido`(){

        val keytype = KeyType.PHONE

        assertTrue(keytype.valida("+5511987654321"))

    }


    @Test
    fun `deve falhar em caso de numero de celular invalido`(){

        val keytype = KeyType.PHONE

        assertFalse(keytype.valida(""))
        assertFalse(keytype.valida("1234567890"))
        assertFalse(keytype.valida(null))
        assertFalse(keytype.valida("++5511987654321"))

    }

    @Test
    fun `deve passar com um email valido`(){
        val keytype = KeyType.EMAIL

        assertTrue(keytype.valida("zup@zup.com.br"))
        assertTrue(keytype.valida("zup.edu@zup.com.br"))
        assertTrue(keytype.valida("zup.edu@zup.com"))

    }

    @Test
    fun `deve falhar em caso de email invalido`(){
        val keytype = KeyType.EMAIL
        assertFalse(keytype.valida("mail.com"))
        assertFalse(keytype.valida(""))
        assertFalse(keytype.valida(null))
    }

}