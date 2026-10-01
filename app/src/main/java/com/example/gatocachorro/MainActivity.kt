package com.example.gatocachorro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gatocachorro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mp: MyPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
//        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left + binding.main.paddingLeft, systemBars.top,
//                systemBars.right + binding.main.paddingRight, systemBars.bottom)
//            insets
//        }
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

        mp = MyPreferences(applicationContext)

        if (mp.getString("name").isNotBlank()) {
            abrirTelaFrases()
            return
        }

        binding.btnFpage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.btn_fpage){
            val nome = binding.mainInputFpage.text.toString().trim()

            if (nome.isBlank()) {
                Toast.makeText(
                    this,
                    "Digite seu nome para continuar.",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            mp.setString("name", nome)

            Toast.makeText(
                this,
                R.string.toast_btn_fpage,
                Toast.LENGTH_SHORT
            ).show()

            abrirTelaFrases()

        }
    }

    private fun abrirTelaFrases() {
        startActivity(Intent(this, TelaFrasesActivity::class.java))
        finish()
    }
}