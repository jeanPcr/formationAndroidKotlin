package com.jeanporcher.tpjeanporcher.tasklist

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeanporcher.tpjeanporcher.databinding.FragmentTaskListBinding
import com.jeanporcher.tpjeanporcher.task.TaskActivity
import java.util.*

class TaskListFragment : Fragment() {
    private var _binding: FragmentTaskListBinding? =null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    private val taskList = mutableListOf<Task>(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )
    private val adapter =TaskListAdapter()

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode==RESULT_OK){
            val resultTask = it.data?.getSerializableExtra("new_task") as? Task
            val taskIndex = taskList.indexOfFirst { task: Task -> task.id == resultTask!!.id}

            if (taskIndex==-1){
                taskList.add(resultTask!!)
            }else{
                taskList[taskIndex] = resultTask!!
            }
            adapter.submitList(taskList.toList())
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        adapter.submitList(taskList.toList())

        //Floating Action Button
        val fab: View = binding.floatingActionButton
        fab.setOnClickListener(){
            val intent = Intent(activity, TaskActivity::class.java)
            startForResult.launch(intent)
        }
        adapter.onDeleteTask = { task ->
            taskList.remove(task)
            adapter.submitList(taskList.toList())
        }
        adapter.onEditTask = { task ->
            val intent = Intent(activity, TaskActivity::class.java)
            val EDIT_TASK = "edit_task"
            intent.putExtra(EDIT_TASK,task)
            startForResult.launch(intent)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }





}