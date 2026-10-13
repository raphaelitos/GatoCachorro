package com.example.gatocachorro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import com.example.gatocachorro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mp: MyPreferences
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

        mp = MyPreferences(applicationContext)

        if (mp.getString("name").isNotBlank()) {
            abrirTelaFrases()
            return
        }

    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.btn_fpage){
            val nome = binding.mainInputFpage.text.toString().trim()

            if (nome.isBlank()) {
                Toast.makeText(
                    this,
                    R.string.toast_nome_vazio,
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            else if (nome.length < 3){
                Toast.makeText(
                    this,
                    R.string.toast_nome_pequeno,
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            mp.setString("name", nome)

            abrirTelaFrases()

        }
    }

    private fun abrirTelaFrases() {
        startActivity(Intent(this, TelaFrasesActivity::class.java))
        finish()
    }
}