package com.example.yellowtimer.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.yellowtimer.R
import com.example.yellowtimer.databinding.FragmentPomodoroBinding
import com.example.yellowtimer.util.Utils.getPomodoroTime
import com.example.yellowtimer.util.Utils.isBreak
import com.example.yellowtimer.util.Utils.isRunning
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit


class PomodoroFragment : Fragment() {
    private var _binding: FragmentPomodoroBinding? = null
    private val binding get() = _binding!!
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var countDownBreak: CountDownTimer
    var number = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPomodoroBinding.inflate(inflater, container, false)
        val view = binding.root

        _binding!!.pomodoroToggle.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                _binding!!.changedText.text = "Basladınız"
                _binding!!.pomodoroToggle.setBackgroundResource(R.drawable.ic_pause)
                createTimer(number)
                isRunning = true
            }else{
                _binding!!.changedText.text = "Durdurdun"
                _binding!!.pomodoroToggle.setBackgroundResource(R.drawable.ic_play)
                stopTimer()
                isRunning = false
            }
        }
        _binding!!.stopPomodoro.setOnClickListener {
            if (isRunning){
                stopTimer()
                val time = context?.let { getPomodoroTime(it) }
                number = (time?.toLong()?.times(1000))?.times(60)!!
                _binding!!.pomodoroTimer.text = String.format(
                    "%d : %d0",
                    TimeUnit.MILLISECONDS.toMinutes(number),
                    TimeUnit.MILLISECONDS.toSeconds(number) -
                            TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(
                                    number
                                )
                            )
                )
                _binding!!.pomodoroToggle.setBackgroundResource(R.drawable.ic_play)
                _binding!!.pomodoroToggle.isChecked = false
            }else{
                Snackbar.make(it,getString(R.string.onPause),Snackbar.LENGTH_LONG).setAction("OK"){
                    Toast.makeText(it.context, getString(R.string.thanks), Toast.LENGTH_SHORT).show()
                }.show()
            }
            if (isBreak){
                stopBreaker()
                val time = context?.let { getPomodoroTime(it) }
                number = (time?.toLong()?.times(1000))?.times(60)!!
                _binding!!.pomodoroTimer.text = String.format(
                    "%d : %d0",
                    TimeUnit.MILLISECONDS.toMinutes(number),
                    TimeUnit.MILLISECONDS.toSeconds(number) -
                            TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(
                                    number
                                )
                            )
                )
                isBreak = false
            }
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = PomodoroFragment()
    }

    private fun createTimer(time: Long) {
        countDownTimer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _binding!!.pomodoroTimer.text = String.format(
                    "%d : %d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(
                                    millisUntilFinished
                                )
                            )
                )
                number = millisUntilFinished
            }

            override fun onFinish() {
                isRunning = false
                createBreak(2*1000*60)
            }

        }.start()
    }

    fun createBreak(time:Long){
        _binding!!.pomodoroToggle.isEnabled = false
        isBreak = true
        countDownBreak = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _binding!!.pomodoroTimer.text = String.format(
                    "%d : %d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(
                                    millisUntilFinished
                                )
                            )
                )
            }
            override fun onFinish() {
                isBreak = false
                val timer = context?.let { getPomodoroTime(it) }
                number = (timer?.toLong()?.times(1000))?.times(60)!!
                createTimer(number)
            }

        }.start()
    }

    override fun onResume() {
        super.onResume()
        val time = context?.let { getPomodoroTime(it) }
        number = (time?.toLong()?.times(1000))?.times(60)!!
        _binding!!.pomodoroTimer.text = String.format(
            "%d : %d",
            TimeUnit.MILLISECONDS.toMinutes(number),
            TimeUnit.MILLISECONDS.toSeconds(number) -
                    TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(
                            number
                        )
                    )
        )
    }
    private fun stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel()
        }
    }
    private fun stopBreaker(){
        _binding!!.pomodoroToggle.isEnabled = true
        if (countDownBreak !=null){
            countDownBreak.cancel()
        }
    }
}