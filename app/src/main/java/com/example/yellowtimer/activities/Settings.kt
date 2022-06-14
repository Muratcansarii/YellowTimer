package com.example.yellowtimer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.yellowtimer.databinding.ActivitySettingsBinding
import com.example.yellowtimer.util.Utils.MY_POMO_TIME_NAME
import com.example.yellowtimer.util.Utils.darkModeState


class Settings : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.modeToggle.isChecked = darkModeState
        binding.modeToggle.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                darkModeState = b
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                darkModeState = b
            }
        }
        binding.savePomodoroTime.setOnClickListener {
            val pomoTime = binding.pomodorTime.text.toString()
            val editor = getSharedPreferences(MY_POMO_TIME_NAME, MODE_PRIVATE).edit()
            editor.putString("timer",pomoTime)
            editor.apply()
            finish()
        }
    }
}