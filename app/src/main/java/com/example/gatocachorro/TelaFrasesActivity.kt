package com.example.gatocachorro

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gatocachorro.databinding.ActivityTelaFrasesBinding

class TelaFrasesActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTelaFrasesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaFrasesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //enableEdgeToEdge()
//        setContentView(R.layout.activity_tela_frases)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        binding.btnCalcAppPage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.btn_calc_app_page){
            Toast.makeText(this, "@string/toast_btn_app_page", Toast.LENGTH_SHORT).show()
        }
    }
}