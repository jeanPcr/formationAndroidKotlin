package com.jeanporcher.tpjeanporcher.tasklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jeanporcher.tpjeanporcher.R
import com.jeanporcher.tpjeanporcher.task.Task


object TasksDiffCallback: DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
       return oldItem == newItem
    }

}

class TaskListAdapter: ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TasksDiffCallback) {
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deleteButton = itemView.findViewById<ImageButton>(R.id.delete_task_btn)
        val editButton =  itemView.findViewById<ImageButton>(R.id.edit_task_btn)
        val textView =  itemView.findViewById<TextView>(R.id.task_title)

        fun bind(task: Task) {
            itemView.apply {
                textView.setText(task.title)
                editButton.setOnClickListener() {
                    onEditTask?.invoke(task)
                }
                deleteButton.setOnClickListener(){
                    onDeleteTask?.invoke(task)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)

        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    var onDeleteTask: ((Task) -> Unit)? = null
    var onEditTask: ((Task) -> Unit)? = null
}