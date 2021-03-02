package com.jeanporcher.tpjeanporcher.repositories

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jeanporcher.tpjeanporcher.network.Api
import com.jeanporcher.tpjeanporcher.task.Task
import com.jeanporcher.tpjeanporcher.task.TaskActivity

class TasksRepository {
    private val tasksWebService = Api.tasksWebService

    private val _tasksList = MutableLiveData<List<Task>>()

    val tasksList: LiveData<List<Task>> = _tasksList

    suspend fun refresh() {
        val tasksResponse = tasksWebService.getTasks()
        if (tasksResponse.isSuccessful) {
            val fetchedTasks = tasksResponse.body()

            _tasksList.value = fetchedTasks!!
        }
    }
    suspend fun create(task: Task) {
        val addedTask = tasksWebService.createTask(task)
        if (addedTask.isSuccessful){
            val editableList = _tasksList.value.orEmpty().toMutableList()
            editableList.add(addedTask.body()!!)
            _tasksList.value = editableList
        }
    }
    suspend fun update(task: Task) {
        val editedTask = tasksWebService.updateTask(task)
        if (editedTask.isSuccessful){
            val editableList = _tasksList.value.orEmpty().toMutableList()
            val position = editableList.indexOfFirst { task.id == it.id }
            editableList[position] = editedTask.body()!!
            _tasksList.value = editableList
        }
    }
    suspend fun delete(task: Task) {
        val deletedTask = tasksWebService.deleteTask(task.id)
        if (deletedTask.isSuccessful){
            val editableList = _tasksList.value.orEmpty().toMutableList()
            editableList.remove(task)
            _tasksList.value = editableList
        }
    }
}