package com.example.gatocachorro.data

import android.content.Context
import com.example.gatocachorro.model.Animal

/**
 * Repositorio dos dados do usuario (nome e animal escolhido).
 * As ViewModels conversam com ele e nunca diretamente com o SharedPreferences.
 */
class UsuarioRepository(context: Context) {

    private val preferences = MyPreferences(context)

    fun getNome(): String = preferences.getString(CHAVE_NOME)

    fun salvarNome(nome: String) {
        preferences.setString(CHAVE_NOME, nome)
    }

    fun limparNome() {
        preferences.setString(CHAVE_NOME, "")
    }

    fun getAnimal(): Animal = Animal.daChave(preferences.getString(CHAVE_ANIMAL))

    fun salvarAnimal(animal: Animal) {
        preferences.setString(CHAVE_ANIMAL, animal.chave)
    }

    private companion object {
        const val CHAVE_NOME = "name"
        const val CHAVE_ANIMAL = "animalSel"
    }
}
