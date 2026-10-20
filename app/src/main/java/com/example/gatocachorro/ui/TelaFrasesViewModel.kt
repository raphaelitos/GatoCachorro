package com.example.gatocachorro.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gatocachorro.R
import com.example.gatocachorro.data.FrasesRepository
import com.example.gatocachorro.data.UsuarioRepository
import com.example.gatocachorro.model.Animal

class TelaFrasesViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioRepository = UsuarioRepository(application)

    private val _nome = MutableLiveData<String>()
    val nome: LiveData<String> = _nome

    private val _animal = MutableLiveData<Animal>()
    val animal: LiveData<Animal> = _animal

    private val _fraseResId = MutableLiveData<Int>()
    val fraseResId: LiveData<Int> = _fraseResId

    /** Recurso da mensagem a exibir; null quando nao ha mensagem pendente. */
    private val _mensagem = MutableLiveData<Int?>()
    val mensagem: LiveData<Int?> = _mensagem

    init {
        _nome.value = usuarioRepository.getNome()
        _animal.value = usuarioRepository.getAnimal()
        sortearNovaFrase()
    }

    fun selecionarAnimal(animal: Animal) {
        usuarioRepository.salvarAnimal(animal)
        _animal.value = animal
        sortearNovaFrase()
    }

    fun sortearNovaFrase() {
        _fraseResId.value = FrasesRepository.sortear(animalAtual())
    }

    fun excluirFraseAtual() {
        val atual = _fraseResId.value ?: return

        if (FrasesRepository.excluir(animalAtual(), atual)) {
            _mensagem.value = R.string.frase_excluida
            sortearNovaFrase()
        }
    }

    /** Chamado pela UI depois de mostrar a mensagem, para ela nao reaparecer ao girar a tela. */
    fun mensagemExibida() {
        _mensagem.value = null
    }

    fun sair() {
        usuarioRepository.limparNome()
    }

    private fun animalAtual(): Animal = _animal.value ?: Animal.PADRAO
}
