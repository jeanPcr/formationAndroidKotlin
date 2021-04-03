package com.jeanporcher.tpjeanporcher.tasklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeanporcher.tpjeanporcher.task.TasksRepository
import com.jeanporcher.tpjeanporcher.task.Task

class TaskListViewModel: ViewModel() {
    private val repository = TasksRepository()
    private val _tasksList = MutableLiveData<List<Task>>()
    val tasksList: LiveData<List<Task>> = _tasksList

    suspend fun refresh() {
        val tasks = repository.loadTasks()
        _tasksList.value = tasks!!

    }
    suspend fun addTask(task: Task) {
        val addedTask = repository.createTask(task)
        val editableList = _tasksList.value.orEmpty().toMutableList()
        editableList.add(addedTask!!)
        _tasksList.value = editableList

    }
    suspend fun editTask(task: Task) {
        val editedTask = repository.updateTask(task)
        val editableList = _tasksList.value.orEmpty().toMutableList()
        val position = editableList.indexOfFirst { task.id == it.id }
        editableList[position] = editedTask!!
        _tasksList.value = editableList

    }
    suspend fun deleteTask(task: Task) {
        val deletedTask = repository.removeTask(task)
        val editableList = _tasksList.value.orEmpty().toMutableList()
        editableList.remove(deletedTask!!)
        _tasksList.value = editableList

    }
}