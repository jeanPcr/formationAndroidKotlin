package com.jeanporcher.tpjeanporcher.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jeanporcher.tpjeanporcher.network.services.TasksWebService
import com.jeanporcher.tpjeanporcher.network.services.UserService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object Api {
    private const val BASE_URL = "https://android-tasks-api.herokuapp.com/api/"
    private const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo1MDYsImV4cCI6MTY0NjIxMTI0MX0.fr6s8KJFuqkq_IZgoaurRypRyPY7RwuPRKLZZ2SsZYc"

    private val jsonSerializer = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val converterFactory = jsonSerializer.asConverterFactory("application/json".toMediaType())

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $TOKEN")
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

   private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
    val tasksWebService: TasksWebService by lazy {
        retrofit.create(TasksWebService::class.java)
    }
}