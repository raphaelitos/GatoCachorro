package com.example.gatocachorro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gatocachorro.databinding.ActivityMainBinding
import com.example.gatocachorro.ui.MainViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left + 20, systemBars.top, systemBars.right + 20, systemBars.bottom)
            insets
        }

        binding.btnFpage.setOnClickListener(this)

        if (viewModel.existeNomeSalvo()) {
            abrirTelaFrases()
        }
    }

    override fun onClick(v: View?) {
        if (v?.id != R.id.btn_fpage) return

        val erro = viewModel.validarESalvarNome(binding.mainInputFpage.text.toString())
        if (erro != null) {
            Toast.makeText(this, erro, Toast.LENGTH_SHORT).show()
        } else {
            abrirTelaFrases()
        }
    }

    private fun abrirTelaFrases() {
        startActivity(Intent(this, TelaFrasesActivity::class.java))
        finish()
    }
}
