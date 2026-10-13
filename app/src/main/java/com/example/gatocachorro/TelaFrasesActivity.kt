package com.example.gatocachorro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gatocachorro.databinding.ActivityMainBinding
import com.example.gatocachorro.databinding.ActivityTelaFrasesBinding

class TelaFrasesActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {
    private lateinit var binding: ActivityTelaFrasesBinding
    private lateinit var mp: MyPreferences
    private lateinit var tipoSel: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTelaFrasesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnCalcAppPage.setOnClickListener(this)
        binding.btnVoltar.setOnClickListener(this)
        binding.imgCat.setOnClickListener(this)
        binding.imgDog.setOnClickListener(this)
        binding.imgCrow.setOnClickListener(this)

        binding.txtCuriosity.setOnLongClickListener(this)

        mp = MyPreferences(applicationContext)
        val salvo = mp.getString("animalSel")
        tipoSel = if (salvo.isNotEmpty()) salvo else "cachorro"

        binding.nomeAppPage.text = mp.getString("name")

        mostrarFrase()
        mudarCor()
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.btn_calc_app_page){
            mostrarFrase()
        }
        else if(v?.id == R.id.img_dog){
            tipoSel = "cachorro"
            mp.setString("animalSel", tipoSel)
            mudarCor()
            mostrarFrase()
        }
        else if(v?.id == R.id.img_cat){
            tipoSel = "gato"
            mp.setString("animalSel", tipoSel)
            mudarCor()
            mostrarFrase()
        }
        else if(v?.id == R.id.img_crow){
            tipoSel = "corvo"
            mp.setString("animalSel", tipoSel)
            mudarCor()
            mostrarFrase()
        }
        else if(v?.id == R.id.btn_voltar){
            mp.setString("name", "")
            //mp.setString("animalSel", "")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onLongClick(v: View?): Boolean {
        val fraseAtual = binding.txtCuriosity.text.toString()

        if (fraseAtual != "Sem frases disponíveis") {

            Frases.excluir(tipoSel, fraseAtual)

            Toast.makeText(this, "Frase excluída com sucesso!", Toast.LENGTH_SHORT).show()
            mostrarFrase()
        }
        return true
    }

    private fun mostrarFrase() {
        binding.txtCuriosity.text = Frases.sortear(tipoSel)
    }

    private fun mudarCor(){
        if (tipoSel == "gato"){
            binding.imgCat.setColorFilter(ContextCompat.getColor(this, R.color.yellow))
            binding.imgDog.setColorFilter(ContextCompat.getColor(this, R.color.white))
            binding.imgCrow.setColorFilter(ContextCompat.getColor(this, R.color.white))
        }
        else if (tipoSel == "corvo"){
            binding.imgCat.setColorFilter(ContextCompat.getColor(this, R.color.white))
            binding.imgDog.setColorFilter(ContextCompat.getColor(this, R.color.white))
            binding.imgCrow.setColorFilter(ContextCompat.getColor(this, R.color.yellow))
        }
        else{
            binding.imgDog.setColorFilter(ContextCompat.getColor(this, R.color.yellow))
            binding.imgCat.setColorFilter(ContextCompat.getColor(this, R.color.white))
            binding.imgCrow.setColorFilter(ContextCompat.getColor(this, R.color.white))
        }
    }
}

object Frases {
    private val frasesGatos = mutableListOf(
        "Os bigodes dos gatos ajudam a perceber objetos próximos e avaliar espaços estreitos.",
        "A língua dos gatos tem pequenas estruturas que ajudam a remover pelos soltos durante a limpeza.",
        "Gatos enxergam melhor que os humanos em ambientes com pouca luz.",
        "Ao arranhar superfícies, os gatos ajudam a remover as camadas antigas das garras.",
        "Gatos adultos normalmente têm 30 dentes."
    )

    private val frasesCachorros = mutableListOf(
        "Cachorros não enxergam apenas em preto e branco: eles distinguem tons de azul e amarelo.",
        "Cachorros possuem glândulas de suor nas almofadinhas das patas.",
        "A respiração ofegante ajuda os cachorros a liberar calor e regular a temperatura do corpo.",
        "Cachorros conseguem ouvir sons mais agudos do que os humanos.",
        "Cachorros têm 28 dentes de leite e normalmente 42 dentes na fase adulta."
    )

    private val frasesPassaros = mutableListOf(
        "Os ossos dos pássaros são ocos e leves para facilitar o voo.",
        "Os beija-flores são os únicos pássaros que conseguem voar para trás.",
        "Os pássaros não têm dentes e usam a moela para triturar os alimentos.",
        "As corujas conseguem girar a cabeça em até 270 graus.",
        "Os pombos possuem uma bússola interna natural baseada no campo magnético para se orientar."
    )


    fun sortear(tipo: String): String {
        val lista = quandoMapearLista(tipo)
        if (lista.isEmpty()) {
            return "Sem frases disponíveis"
        }
        return lista.random()
    }

    fun excluir(tipo: String, frase: String) {
        val lista = quandoMapearLista(tipo)
        lista.remove(frase)
    }


    private fun quandoMapearLista(tipo: String): MutableList<String> {
        return when (tipo) {
            "gato" -> frasesGatos
            "corvo" -> frasesPassaros
            else -> frasesCachorros
        }
    }
}