package com.myapp.calculator

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.myapp.calculator.databinding.ActivityChangeThemeBinding

class ChangeThemeActivity : AppCompatActivity() {

    lateinit var switchBinding : ActivityChangeThemeBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        switchBinding = ActivityChangeThemeBinding.inflate(layoutInflater)
        val view = switchBinding.root
        setContentView(view)

        switchBinding.toolbar2.setNavigationOnClickListener {
            finish()
        }

        switchBinding.mySwitch.setOnCheckedChangeListener { buttonView, isChecked ->

            sharedPreferences = this.getSharedPreferences("Dark Theme", Context.MODE_PRIVATE)
            val editor  = sharedPreferences.edit()

            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("switch", true)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("switch", false)
            }

            editor.apply()
        }
    }

    override fun onResume() {
        super.onResume()

        sharedPreferences = this.getSharedPreferences("Dark Theme", Context.MODE_PRIVATE)
        val isDark = sharedPreferences.getBoolean("switch", false)
        switchBinding.mySwitch.isChecked = isDark

    }
}