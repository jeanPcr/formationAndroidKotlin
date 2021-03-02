package com.jeanporcher.tpjeanporcher.tasklist

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeanporcher.tpjeanporcher.R
import com.jeanporcher.tpjeanporcher.databinding.FragmentTaskListBinding
import com.jeanporcher.tpjeanporcher.network.Api
import com.jeanporcher.tpjeanporcher.repositories.TasksRepository
import com.jeanporcher.tpjeanporcher.task.Task
import com.jeanporcher.tpjeanporcher.task.TaskActivity
import kotlinx.coroutines.launch

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

    private val adapter = TaskListAdapter()
    private val tasksRepository = TasksRepository()

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode==RESULT_OK){
            val resultTask = it.data?.getSerializableExtra("new_task") as? Task
            val list = tasksRepository.tasksList.value!!
            val taskIndex = list.indexOfFirst { task: Task -> task.id == resultTask!!.id}

            lifecycleScope.launch {
                if (taskIndex==-1){

                        tasksRepository.create(resultTask!!)
                }else{

                        tasksRepository.update(resultTask!!)
                }
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        tasksRepository.tasksList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list.toList())
        }

        //Floating Action Button
        val fab: View = binding.floatingActionButton
        fab.setOnClickListener(){
            val intent = Intent(activity, TaskActivity::class.java)
            startForResult.launch(intent)
        }
        adapter.onDeleteTask = { task ->
            lifecycleScope.launch {
                tasksRepository.delete(task)
            }
        }
        adapter.onEditTask = { task ->
                val intent = Intent(activity, TaskActivity::class.java)
                val EDIT_TASK = "edit_task"
                intent.putExtra(EDIT_TASK,task)
                startForResult.launch(intent)
        }
    }


    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            tasksRepository.refresh()
            val userInfoTxt = view?.findViewById<TextView>(R.id.user_info)
            val userInfo = Api.userService.getInfo().body()!!
            userInfoTxt?.text = "${userInfo.firstName} ${userInfo.lastName}'s to-do list"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }





}