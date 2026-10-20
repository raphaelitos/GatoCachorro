package com.example.gatocachorro.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gatocachorro.R
import com.example.gatocachorro.databinding.ActivityTelaFrasesBinding
import com.example.gatocachorro.model.Animal

class TelaFrasesActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {

    private lateinit var binding: ActivityTelaFrasesBinding
    private val viewModel: TelaFrasesViewModel by viewModels()

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

        observarEstado()
    }

    private fun observarEstado() {
        viewModel.nome.observe(this) { nome ->
            binding.nomeAppPage.text = nome
        }
        viewModel.animal.observe(this) { animal ->
            mudarCor(animal)
        }
        viewModel.frase.observe(this) { frase ->
            binding.txtCuriosity.text = frase
        }
        viewModel.mensagem.observe(this) { resId ->
            if (resId != null) {
                Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
                viewModel.mensagemExibida()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_calc_app_page -> viewModel.sortearNovaFrase()
            R.id.img_dog -> viewModel.selecionarAnimal(Animal.CACHORRO)
            R.id.img_cat -> viewModel.selecionarAnimal(Animal.GATO)
            R.id.img_crow -> viewModel.selecionarAnimal(Animal.CORVO)
            R.id.btn_voltar -> {
                viewModel.sair()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onLongClick(v: View?): Boolean {
        if (v?.id != R.id.txt_curiosity) return false
        viewModel.excluirFraseAtual()
        return true
    }

    /** Apenas a apresentacao visual permanece na Activity. */
    private fun mudarCor(animal: Animal) {
        val corDestaque = ContextCompat.getColor(this, R.color.yellow)
        val corPadrao = ContextCompat.getColor(this, R.color.white)

        binding.imgCat.setColorFilter(if (animal == Animal.GATO) corDestaque else corPadrao)
        binding.imgDog.setColorFilter(if (animal == Animal.CACHORRO) corDestaque else corPadrao)
        binding.imgCrow.setColorFilter(if (animal == Animal.CORVO) corDestaque else corPadrao)
    }
}
