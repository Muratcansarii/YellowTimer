package com.example.yellowtimer.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.yellowtimer.R
import com.example.yellowtimer.databinding.ActivityMainBinding
import com.example.yellowtimer.fragments.PomodoroFragment
import com.example.yellowtimer.fragments.TaskFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        loadFragment(PomodoroFragment.newInstance())
        binding.toolbar.title = "Pomodoro"
        actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.drawer, R.string.nav_open, R.string.nav_close)
        binding.drawer.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_pomodoro ->{
                    loadFragment(PomodoroFragment.newInstance())
                    binding.toolbar.title = "Pomodoro"
                    true
                }
                R.id.nav_tasks ->{
                    loadFragment(TaskFragment.newInstance())
                    binding.toolbar.title = "Tasks"
                    true
                }
                else -> false
            }
        }
        binding.navView.setNavigationItemSelectedListener(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle!!.onOptionsItemSelected(item)){
            when(item.itemId){
                R.id.drw_settings -> {
                    true
                }
                R.id.drw_about -> {
                    true
                }
                else -> false
            }
        }else super.onOptionsItemSelected(item)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.drw_settings ->{
                startActivity(Intent(this,Settings::class.java))
            }
            R.id.drw_about ->{
                startActivity(Intent(this,About::class.java))
            }
        }
        return true
    }

}