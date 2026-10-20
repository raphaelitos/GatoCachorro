package com.example.gatocachorro.data

import android.content.Context
import com.example.gatocachorro.R
import com.example.gatocachorro.data.remote.ClientRetrofit
import com.example.gatocachorro.data.remote.entity.BirdFactEntity
import com.example.gatocachorro.data.remote.entity.CatFactEntity
import com.example.gatocachorro.data.remote.entity.DogFactEntity
import com.example.gatocachorro.model.Animal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

/**
 * Repositorio das curiosidades.
 * A fonte principal sao as APIs web; as frases locais (strings.xml) sao a contencao
 * usada quando a API falha.
 */
class FrasesRepository(context: Context) {

    private val context = context.applicationContext

    private val catService = ClientRetrofit.createCatFactService()
    private val dogService = ClientRetrofit.createDogFactService()
    private val birdService = ClientRetrofit.createBirdFactService()

    /**
     * Busca uma frase de forma assincrona e entrega o resultado em [aoReceber]:
     * - frase: o texto, ou null se nao houver nenhuma frase disponivel;
     * - apiFalhou: true quando a API nao respondeu e a frase veio da lista local.
     */
    fun buscarFrase(animal: Animal, aoReceber: (frase: String?, apiFalhou: Boolean) -> Unit) {
        buscarFrase(animal, 1, aoReceber)
    }

    /** A frase excluida nao volta a aparecer enquanto o app estiver aberto. */
    fun excluir(frase: String) {
        excluidas.add(frase)
    }

    private fun buscarFrase(
        animal: Animal,
        tentativa: Int,
        aoReceber: (frase: String?, apiFalhou: Boolean) -> Unit
    ) {
        val aoReceberDaApi: (String?) -> Unit = { frase ->
            when {
                frase.isNullOrBlank() -> aoReceber(fraseLocal(animal), true)
                frase !in excluidas -> aoReceber(frase, false)
                // A API devolveu uma frase excluida: pede outra algumas vezes antes de desistir.
                tentativa < MAX_TENTATIVAS -> buscarFrase(animal, tentativa + 1, aoReceber)
                else -> aoReceber(fraseLocal(animal), false)
            }
        }

        when (animal) {
            Animal.GATO -> enfileirar(catService.getFact(), aoReceberDaApi) { it.fact }
            Animal.CACHORRO -> enfileirar(dogService.getFact(), aoReceberDaApi) {
                it.data.firstOrNull()?.attributes?.body
            }
            Animal.CORVO -> enfileirar(
                birdService.getFacts(promptAves(), "array of bird facts"),
                aoReceberDaApi
            ) { it.facts.firstOrNull() }
        }
    }

    /**
     * Coloca a chamada na fila com enqueue(). O Retrofit executa a requisicao em segundo
     * plano e chama onResponse/onFailure de volta na thread principal.
     * [extrairFrase] diz como tirar o texto de dentro de cada entidade.
     */
    private fun <T> enfileirar(
        call: Call<T>,
        aoReceberDaApi: (String?) -> Unit,
        extrairFrase: (T) -> String?
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                // body() e null quando o servidor responde com erro (ex: HTTP 500).
                val entidade = response.body()
                aoReceberDaApi(entidade?.let(extrairFrase))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                // Sem internet, timeout, JSON invalido...
                aoReceberDaApi(null)
            }
        })
    }

    /** O jsongpt gera o texto por IA, entao pedimos no idioma do aparelho. */
    private fun promptAves(): String {
        val idioma = if (Locale.getDefault().language == "pt") "Brazilian Portuguese" else "English"
        return "Generate 1 bird fact in $idioma"
    }

    private fun fraseLocal(animal: Animal): String? {
        val ids = when (animal) {
            Animal.GATO -> frasesGatos
            Animal.CACHORRO -> frasesCachorros
            Animal.CORVO -> frasesAves
        }
        return ids.map { context.getString(it) }
            .filter { it !in excluidas }
            .randomOrNull()
    }

    /** Estado compartilhado por todas as instancias enquanto o app estiver em execucao. */
    private companion object {
        const val MAX_TENTATIVAS = 3

        val excluidas = mutableSetOf<String>()

        val frasesGatos = listOf(
            R.string.frase_gato_1,
            R.string.frase_gato_2,
            R.string.frase_gato_3,
            R.string.frase_gato_4,
            R.string.frase_gato_5
        )

        val frasesCachorros = listOf(
            R.string.frase_cachorro_1,
            R.string.frase_cachorro_2,
            R.string.frase_cachorro_3,
            R.string.frase_cachorro_4,
            R.string.frase_cachorro_5
        )

        val frasesAves = listOf(
            R.string.frase_ave_1,
            R.string.frase_ave_2,
            R.string.frase_ave_3,
            R.string.frase_ave_4,
            R.string.frase_ave_5
        )
    }
}
