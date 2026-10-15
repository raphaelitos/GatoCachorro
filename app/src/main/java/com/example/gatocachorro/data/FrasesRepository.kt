package com.example.gatocachorro.data

import com.example.gatocachorro.R

/** Guarda as curiosidades disponiveis durante a execucao do aplicativo. */
object FrasesRepository {

    private val frasesGatos = mutableListOf(
        R.string.frase_gato_1,
        R.string.frase_gato_2,
        R.string.frase_gato_3,
        R.string.frase_gato_4,
        R.string.frase_gato_5
    )

    private val frasesCachorros = mutableListOf(
        R.string.frase_cachorro_1,
        R.string.frase_cachorro_2,
        R.string.frase_cachorro_3,
        R.string.frase_cachorro_4,
        R.string.frase_cachorro_5
    )

    private val frasesAves = mutableListOf(
        R.string.frase_ave_1,
        R.string.frase_ave_2,
        R.string.frase_ave_3,
        R.string.frase_ave_4,
        R.string.frase_ave_5
    )

    fun sortear(tipo: String): Int {
        val frases = listaDoAnimal(tipo)
        return if (frases.isEmpty()) R.string.sem_frases_disponiveis else frases.random()
    }

    fun excluir(tipo: String, fraseResId: Int): Boolean {
        if (fraseResId == R.string.sem_frases_disponiveis) return false
        return listaDoAnimal(tipo).remove(fraseResId)
    }

    private fun listaDoAnimal(tipo: String): MutableList<Int> = when (tipo) {
        "gato" -> frasesGatos
        "corvo" -> frasesAves // Identificador interno usado pelo projeto original.
        else -> frasesCachorros
    }
}
