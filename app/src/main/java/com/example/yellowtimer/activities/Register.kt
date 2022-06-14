package com.example.yellowtimer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.yellowtimer.R
import com.example.yellowtimer.database.AppDB
import com.example.yellowtimer.database.UserEntity
import com.example.yellowtimer.databinding.ActivityRegisterBinding
import kotlin.concurrent.thread

class Register : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var appDB: AppDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        appDB = AppDB.getInstance(this)!!

        binding.registerUser.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val pass = binding.userPass.text.toString()
            val rePass = binding.userRePass.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty() && rePass.isNotEmpty()){
                if (pass == rePass){
                    thread {
                        appDB.appDao()?.insertUser(UserEntity(email,pass))
                        startActivity(Intent(this,Login::class.java))
                        finish()
                    }
                }else{
                    Toast.makeText(this, getString(R.string.passwordDontMatch), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, getString(R.string.incompleteFields), Toast.LENGTH_SHORT).show()
            }
        }
    }
}