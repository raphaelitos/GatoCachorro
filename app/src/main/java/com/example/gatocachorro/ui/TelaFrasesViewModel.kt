package com.example.gatocachorro.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gatocachorro.MyPreferences
import com.example.gatocachorro.R
import com.example.gatocachorro.data.FrasesRepository

class TelaFrasesViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences = MyPreferences(application)

    private val _nome = MutableLiveData<String>()
    val nome: LiveData<String> = _nome

    private val _animal = MutableLiveData<String>()
    val animal: LiveData<String> = _animal

    private val _fraseResId = MutableLiveData<Int>()
    val fraseResId: LiveData<Int> = _fraseResId

    init {
        _nome.value = preferences.getString("name")
        val salvo = preferences.getString("animalSel")
        _animal.value = if (salvo in setOf("gato", "cachorro", "corvo")) salvo else "cachorro"
        sortearNovaFrase()
    }

    fun selecionarAnimal(tipo: String) {
        if (tipo !in setOf("gato", "cachorro", "corvo")) return
        preferences.setString("animalSel", tipo)
        _animal.value = tipo
        sortearNovaFrase()
    }

    fun sortearNovaFrase() {
        _fraseResId.value = FrasesRepository.sortear(_animal.value ?: "cachorro")
    }

    fun excluirFraseAtual(): Boolean {
        val atual = _fraseResId.value ?: return false
        if (atual == R.string.sem_frases_disponiveis) return false

        val excluida = FrasesRepository.excluir(_animal.value ?: "cachorro", atual)
        if (excluida) sortearNovaFrase()
        return excluida
    }

    fun limparNomeSalvo() {
        preferences.setString("name", "")
        _nome.value = ""
    }
}