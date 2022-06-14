package com.example.yellowtimer.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

import android.content.SharedPreferences
object Utils {
    var darkModeState = false
    const val MY_POMO_TIME_NAME = "pomodoroTimer"
    var isRunning = false
    var isBreak = false
    fun getPomodoroTime(context:Context):String?{
        val prefs: SharedPreferences = context.getSharedPreferences(MY_POMO_TIME_NAME, MODE_PRIVATE)
        return prefs.getString("timer", "25")
    }
}