package com.example.gatocachorro.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.gatocachorro.MyPreferences
import com.example.gatocachorro.R

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences = MyPreferences(application)

    fun existeNomeSalvo(): Boolean = preferences.getString("name").isNotBlank()

    /** Retorna o recurso da mensagem de erro, ou null se o nome foi salvo. */
    fun validarESalvarNome(texto: String): Int? {
        val nome = texto.trim()
        return when {
            nome.isBlank() -> R.string.toast_nome_vazio
            nome.length < 3 -> R.string.toast_nome_pequeno
            else -> {
                preferences.setString("name", nome)
                null
            }
        }
    }
}