package com.jeanporcher.tpjeanporcher.tasklist

import java.io.Serializable

data class Task(val id:String, val title: String, val description: String  = "description par défaut de la task"):Serializable
