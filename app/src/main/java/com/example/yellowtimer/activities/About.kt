package com.example.yellowtimer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yellowtimer.databinding.ActivityAboutBinding

class About : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}