package com.example.gatocachorro.data

import android.content.Context
import android.content.SharedPreferences

/** Fonte de dados local: so sabe ler e gravar textos no SharedPreferences. */
class MyPreferences(context: Context) {

    private val sp: SharedPreferences =
        context.getSharedPreferences("CHAVE_ACESSO", Context.MODE_PRIVATE)

    fun setString(key: String, str: String) {
        sp.edit().putString(key, str).apply()
    }

    fun getString(key: String): String {
        return sp.getString(key, "") ?: ""
    }
}
