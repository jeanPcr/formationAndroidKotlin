package com.jeanporcher.tpjeanporcher.task

import kotlinx.serialization.SerialName
import java.io.Serializable
import java.util.*

@kotlinx.serialization.Serializable
data class Task(
                @SerialName("id")
                val id:String,
                @SerialName("title")
                val title: String,
                @SerialName("description")
                val description: String  = "description par défaut de la task",
                @SerialName("due_date")
                val dueDate: String  = Date().toString()
                ):Serializable
