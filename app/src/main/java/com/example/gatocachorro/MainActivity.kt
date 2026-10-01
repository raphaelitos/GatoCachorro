package com.example.gatocachorro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gatocachorro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

//        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left + binding.main.paddingLeft, systemBars.top,
//                systemBars.right + binding.main.paddingRight, systemBars.bottom)
//            insets
//        }

        binding.btnFpage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.btn_fpage){
            Toast.makeText(this, "@string/toast_btn_fpage", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, TelaFrasesActivity::class.java))

        }
    }
}