package com.jeanporcher.tpjeanporcher.task

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.jeanporcher.tpjeanporcher.R
import com.jeanporcher.tpjeanporcher.tasklist.Task
import com.jeanporcher.tpjeanporcher.tasklist.TaskListFragment
import java.util.*

class TaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        val taskBtn = findViewById<Button>(R.id.task_button)
        val title = findViewById<EditText>(R.id.new_task_tile)
        val description = findViewById<EditText>(R.id.new_task_description)
        val NEW_TASK = "new_task"
        val task =  intent.getSerializableExtra("edit_task") as? Task

        when (intent?.action) {
            Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                       description.setText(it)
                    }
                } else{
                    val toast = Toast(this)
                    toast.setText("Error: Only text supported into the description")
                }
            }
            else -> {
                ///edit task

                title.setText(task?.title)
                description.setText(task?.description)
                if (task!=null)taskBtn.setText("Edit")

            }
        }
        taskBtn.setOnClickListener(){
            var newTask = Task(id = task?.id ?: UUID.randomUUID().toString(), title = title.text.toString(),description = description.text.toString())
            if (newTask.title.equals("")){
                Toast.makeText(this,"Please enter a title.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (description.text.toString().equals("")){
                newTask = Task(id = task?.id ?: UUID.randomUUID().toString(), title = title.text.toString())
            }
            intent.putExtra(NEW_TASK,newTask)
            setResult(RESULT_OK,intent)
            finish()
        }


    }


}