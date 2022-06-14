package com.example.yellowtimer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.yellowtimer.R
import com.example.yellowtimer.database.AppDB
import com.example.yellowtimer.database.UserEntity
import com.example.yellowtimer.databinding.ActivityLoginBinding
import kotlin.concurrent.thread

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var appDB: AppDB
    private lateinit var user : List<UserEntity?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        appDB = AppDB.getInstance(this)!!
        binding.loginUser.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val pass = binding.userPass.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()){
                thread {
                    user = appDB.appDao()?.loadUser(email,pass)!!
                    if (user.isNullOrEmpty()){
                        runOnUiThread {
                            Toast.makeText(this, getString(R.string.userNotFound), Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }
                }
            }else{
                Toast.makeText(this, getString(R.string.incompleteFields), Toast.LENGTH_SHORT).show()
            }
        }
        binding.registerLabel.setOnClickListener {
            startActivity(Intent(this,Register::class.java))
            finish()
        }
    }
}