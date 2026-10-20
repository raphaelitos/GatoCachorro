package com.example.gatocachorro.model

/**
 * Modelo dos animais suportados pelo app.
 * A [chave] e o valor gravado nas preferencias, mantido igual ao das versoes anteriores.
 */
enum class Animal(val chave: String) {
    GATO("gato"),
    CACHORRO("cachorro"),
    CORVO("corvo");

    companion object {
        val PADRAO = CACHORRO

        fun daChave(chave: String): Animal = entries.find { it.chave == chave } ?: PADRAO
    }
}
