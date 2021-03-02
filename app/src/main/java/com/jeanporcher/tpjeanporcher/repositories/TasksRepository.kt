package com.jeanporcher.tpjeanporcher.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jeanporcher.tpjeanporcher.network.Api
import com.jeanporcher.tpjeanporcher.task.Task

class TasksRepository {
    private val tasksWebService = Api.tasksWebService

    private val _taskList = MutableLiveData<List<Task>>()

    val taskList: LiveData<List<Task>> = _taskList

    suspend fun refresh() {
        val tasksResponse = tasksWebService.getTasks()
        if (tasksResponse.isSuccessful) {
            val fetchedTasks = tasksResponse.body()

            _taskList.value = fetchedTasks!!
        }
    }
}