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
    private val frasesRepository = FrasesRepository(application)

    private val _nome = MutableLiveData<String>()
    val nome: LiveData<String> = _nome

    private val _animal = MutableLiveData<Animal>()
    val animal: LiveData<Animal> = _animal

    /** Texto exibido na area da curiosidade (frase, "carregando..." ou "sem frases"). */
    private val _frase = MutableLiveData<String>()
    val frase: LiveData<String> = _frase

    /** Recurso da mensagem a exibir; null quando nao ha mensagem pendente. */
    private val _mensagem = MutableLiveData<Int?>()
    val mensagem: LiveData<Int?> = _mensagem

    /** Frase realmente carregada; null enquanto carrega ou quando nao ha frase para excluir. */
    private var fraseAtual: String? = null

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
        val animal = animalAtual()
        fraseAtual = null
        _frase.value = texto(R.string.carregando_frase)

        frasesRepository.buscarFrase(animal) { frase, apiFalhou ->
            // Se o usuario trocou de animal antes da resposta chegar, ela ja nao serve.
            if (animal != animalAtual()) return@buscarFrase

            fraseAtual = frase
            _frase.value = frase ?: texto(R.string.sem_frases_disponiveis)
            if (apiFalhou) _mensagem.value = R.string.erro_api_frase_local
        }
    }

    fun excluirFraseAtual() {
        val atual = fraseAtual ?: return

        frasesRepository.excluir(atual)
        _mensagem.value = R.string.frase_excluida
        sortearNovaFrase()
    }

    /** Chamado pela UI depois de mostrar a mensagem, para ela nao reaparecer ao girar a tela. */
    fun mensagemExibida() {
        _mensagem.value = null
    }

    fun sair() {
        usuarioRepository.limparNome()
    }

    private fun animalAtual(): Animal = _animal.value ?: Animal.PADRAO

    private fun texto(resId: Int): String = getApplication<Application>().getString(resId)
}
