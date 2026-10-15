package com.example.gatocachorro

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
import com.example.gatocachorro.databinding.ActivityTelaFrasesBinding
import com.example.gatocachorro.ui.TelaFrasesViewModel

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
        viewModel.animal.observe(this) { tipo ->
            mudarCor(tipo)
        }
        viewModel.fraseResId.observe(this) { resId ->
            binding.txtCuriosity.setText(resId)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_calc_app_page -> viewModel.sortearNovaFrase()
            R.id.img_dog -> viewModel.selecionarAnimal("cachorro")
            R.id.img_cat -> viewModel.selecionarAnimal("gato")
            R.id.img_crow -> viewModel.selecionarAnimal("corvo")
            R.id.btn_voltar -> {
                viewModel.limparNomeSalvo()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onLongClick(v: View?): Boolean {
        if (v?.id != R.id.txt_curiosity) return false
        if (viewModel.excluirFraseAtual()) {
            Toast.makeText(this, R.string.frase_excluida, Toast.LENGTH_SHORT).show()
        }
        return true
    }

    /** Apenas a apresentacao visual permanece na Activity. */
    private fun mudarCor(tipo: String) {
        val corDestaque = ContextCompat.getColor(this, R.color.yellow)
        val corPadrao = ContextCompat.getColor(this, R.color.white)

        binding.imgCat.setColorFilter(if (tipo == "gato") corDestaque else corPadrao)
        binding.imgDog.setColorFilter(if (tipo == "cachorro") corDestaque else corPadrao)
        binding.imgCrow.setColorFilter(if (tipo == "corvo") corDestaque else corPadrao)
    }
}
