package com.jeanporcher.tpjeanporcher.task

import com.jeanporcher.tpjeanporcher.network.Api

class TasksRepository {
    private val tasksWebService = Api.tasksWebService

    suspend fun loadTasks(): List<Task>?{
        val response = tasksWebService.getTasks()
        return if (response.isSuccessful) response.body() else null
    }
    suspend fun createTask(task: Task): Task? {
        val addedTask = tasksWebService.createTask(task)
        return if (addedTask.isSuccessful){
            addedTask.body()!!
        }
        else null
    }

    suspend fun updateTask(task: Task): Task? {
        val editedTask = tasksWebService.updateTask(task)
        return if (editedTask.isSuccessful){
            editedTask.body()!!
        }
        else null
    }
    suspend fun removeTask(task: Task): Task? {
        val deletedTask = tasksWebService.deleteTask(task.id)
        return if (deletedTask.isSuccessful)task else null
    }
}