package com.example.gatocachorro.data

import com.example.gatocachorro.R
import com.example.gatocachorro.model.Animal

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

    fun sortear(animal: Animal): Int {
        val frases = listaDoAnimal(animal)
        return if (frases.isEmpty()) R.string.sem_frases_disponiveis else frases.random()
    }

    fun excluir(animal: Animal, fraseResId: Int): Boolean {
        if (fraseResId == R.string.sem_frases_disponiveis) return false
        return listaDoAnimal(animal).remove(fraseResId)
    }

    private fun listaDoAnimal(animal: Animal): MutableList<Int> = when (animal) {
        Animal.GATO -> frasesGatos
        Animal.CACHORRO -> frasesCachorros
        Animal.CORVO -> frasesAves
    }
}
