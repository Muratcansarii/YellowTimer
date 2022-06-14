package com.example.yellowtimer.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yellowtimer.database.AppDB
import com.example.yellowtimer.database.TaskEntity
import com.example.yellowtimer.R
import com.example.yellowtimer.activities.AddTask
import kotlin.concurrent.thread

class TaskAdapter(private val context:Context,private val list : ArrayList<TaskEntity?>): RecyclerView.Adapter<TaskAdapter.TViewHolder>() {

    inner class TViewHolder(view:View):RecyclerView.ViewHolder(view){
        val appDB : AppDB = AppDB.getInstance(context)!!
        val taskName : TextView = view.findViewById(R.id.taskName)
        val delete : ImageView = view.findViewById(R.id.taskDelete)
        val edit : ImageView = view.findViewById(R.id.taskEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_row,parent,false)
        return TViewHolder(view)
    }

    override fun onBindViewHolder(holder: TViewHolder, position: Int) {
        val tasks = list[position]
        holder.taskName.text = tasks?.name

        holder.delete.setOnClickListener {
            thread {
                holder.appDB.appDao()?.delete(TaskEntity(tasks!!.id,tasks.name))
            }
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size)
        }

        holder.edit.setOnClickListener {
            val i = Intent(it.context,AddTask::class.java)
            i.putExtra("id",tasks?.id)
            i.putExtra("task",tasks?.name)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}