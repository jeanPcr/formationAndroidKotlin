package com.jeanporcher.tpjeanporcher.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeanporcher.tpjeanporcher.R
import com.jeanporcher.tpjeanporcher.databinding.FragmentTaskListBinding
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView
        val adapter =TaskListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        adapter.submitList(taskList.toList())

        //Floating Action Button
        val fab: View = binding.floatingActionButton
        fab.setOnClickListener(){
            val added = taskList.add(Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}"))
            if (added) adapter.submitList(taskList.toList())
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }





}