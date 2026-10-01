package com.example.gatocachorro

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.gatocachorro.databinding.ActivityTelaFrasesBinding

class TelaFrasesActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTelaFrasesBinding
    private lateinit var mp: MyPreferences
    private var tipoSel: String = "cachorro"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
//        setContentView(R.layout.activity_tela_frases)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        binding = ActivityTelaFrasesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalcAppPage.setOnClickListener(this)
        binding.imgCat.setOnClickListener(this)
        binding.imgDog.setOnClickListener(this)

        mp = MyPreferences(applicationContext)

        binding.nomeAppPage.text = mp.getString("name")
        mostrarFrase()
        mudarCor()
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.btn_calc_app_page){
            mostrarFrase()
            //Toast.makeText(this, "@string/toast_btn_app_page", Toast.LENGTH_SHORT).show()
        }
        else if(v?.id == R.id.img_dog){
            tipoSel = "cachorro"
            mudarCor()
            mostrarFrase()
        }
        else if(v?.id == R.id.img_cat){
            tipoSel = "gato"
            mudarCor()
            mostrarFrase()
        }
    }

    private fun mostrarFrase() {
        binding.txtCuriosity.text = Frases.sortear(tipoSel)
    }

    private fun mudarCor(){
        if (tipoSel == "gato"){
            binding.imgCat.setColorFilter(ContextCompat.getColor(this, R.color.yellow))
            binding.imgDog.setColorFilter(ContextCompat.getColor(this, R.color.white))
        }
        else{
            binding.imgDog.setColorFilter(ContextCompat.getColor(this, R.color.yellow))
            binding.imgCat.setColorFilter(ContextCompat.getColor(this, R.color.white))
        }
    }
}

object Frases {
    private val frasesGatos = listOf(
        "Os bigodes dos gatos ajudam a perceber objetos próximos e avaliar espaços estreitos.",
        "A língua dos gatos tem pequenas estruturas que ajudam a remover pelos soltos durante a limpeza.",
        "Gatos enxergam melhor que os humanos em ambientes com pouca luz.",
        "Ao arranhar superfícies, os gatos ajudam a remover as camadas antigas das garras.",
        "Gatos adultos normalmente têm 30 dentes."
    )

    private val frasesCachorros = listOf(
        "Cachorros não enxergam apenas em preto e branco: eles distinguem tons de azul e amarelo.",
        "Cachorros possuem glândulas de suor nas almofadinhas das patas.",
        "A respiração ofegante ajuda os cachorros a liberar calor e regular a temperatura do corpo.",
        "Cachorros conseguem ouvir sons mais agudos do que os humanos.",
        "Cachorros têm 28 dentes de leite e normalmente 42 dentes na fase adulta."
    )

    fun sortear(tipo: String): String {
        if(tipo == "gato") return frasesGatos.random()
        else return frasesCachorros.random()
    }
}