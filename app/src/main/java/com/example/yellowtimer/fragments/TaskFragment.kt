package com.example.yellowtimer.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yellowtimer.activities.AddTask
import com.example.yellowtimer.adapter.TaskAdapter
import com.example.yellowtimer.database.AppDB
import com.example.yellowtimer.database.TaskEntity
import com.example.yellowtimer.databinding.FragmentTaskBinding
import kotlin.concurrent.thread


class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var appDB: AppDB
    private lateinit var taskItems : List<TaskEntity?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDB = context?.let { AppDB.getInstance(it) }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val view = binding.root
        _binding!!.taskRecyclerView.layoutManager = LinearLayoutManager(context)
        _binding!!.addTask.setOnClickListener {
            startActivity(Intent(context, AddTask::class.java))
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = TaskFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        loadRecyclerView()
    }

    private fun loadRecyclerView(){
        thread {
            taskItems = appDB.appDao()!!.loadAllTasks()
            if (taskItems.isNotEmpty()){
                (context as Activity).runOnUiThread {
                    _binding!!.taskRecyclerView.adapter = TaskAdapter(requireContext(),
                        taskItems as ArrayList<TaskEntity?>
                    )
                }
            }else{
                (context as Activity).runOnUiThread {
                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}