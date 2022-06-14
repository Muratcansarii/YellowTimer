package com.example.yellowtimer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.yellowtimer.database.AppDB
import com.example.yellowtimer.database.TaskEntity
import com.example.yellowtimer.databinding.ActivityAddTaskBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread

class AddTask : AppCompatActivity() {
    private lateinit var binding : ActivityAddTaskBinding
    private lateinit var appDB: AppDB
    private var hasExtra = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        appDB = AppDB.getInstance(this)!!
        val intent = intent
        if (intent != null && intent.hasExtra("id")){
            binding.addtaskET.setText(intent.getStringExtra("task"))
            hasExtra = true
        }
        binding.saveTask.setOnClickListener {
            if (hasExtra){
                thread {
                    if (binding.addtaskET.text.isNotEmpty()){
                        appDB.appDao()?.updateTask(TaskEntity(intent.getIntExtra("id",0),binding.addtaskET.text.toString()))
                        finish()
                    }else{
                        Snackbar.make(it, "Please Fill the Field", Snackbar.LENGTH_INDEFINITE)
                            .setAction("OK"){
                                Toast.makeText(it.context, "Thanks", Toast.LENGTH_SHORT).show()
                            }
                            .show()

                    }
                }
            }else{
                thread {
                    if (binding.addtaskET.text.isNotEmpty()){
                        appDB.appDao()?.insertTask(TaskEntity(binding.addtaskET.text.toString()))
                        finish()
                    }else{
                        Snackbar.make(it, "Please Fill the Field", Snackbar.LENGTH_INDEFINITE)
                            .setAction("OK"){
                                Toast.makeText(it.context, "Thanks", Toast.LENGTH_SHORT).show()
                            }
                            .show()
                    }
                }
            }
        }
    }
}