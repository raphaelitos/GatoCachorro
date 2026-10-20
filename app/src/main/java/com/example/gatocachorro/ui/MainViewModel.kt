package com.example.gatocachorro.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gatocachorro.R
import com.example.gatocachorro.data.UsuarioRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val usuarioRepository = UsuarioRepository(application)

    /** Recurso da mensagem de erro a exibir; null quando nao ha erro pendente. */
    private val _erro = MutableLiveData<Int?>()
    val erro: LiveData<Int?> = _erro

    /** Fica true quando a tela de frases deve ser aberta. */
    private val _abrirTelaFrases = MutableLiveData(false)
    val abrirTelaFrases: LiveData<Boolean> = _abrirTelaFrases

    init {
        if (usuarioRepository.getNome().isNotBlank()) {
            _abrirTelaFrases.value = true
        }
    }

    fun salvarNome(texto: String) {
        val nome = texto.trim()
        when {
            nome.isBlank() -> _erro.value = R.string.toast_nome_vazio
            nome.length < 3 -> _erro.value = R.string.toast_nome_pequeno
            else -> {
                usuarioRepository.salvarNome(nome)
                _abrirTelaFrases.value = true
            }
        }
    }

    /** Chamado pela UI depois de mostrar o erro, para ele nao reaparecer ao girar a tela. */
    fun erroExibido() {
        _erro.value = null
    }
}
