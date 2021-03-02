package com.jeanporcher.tpjeanporcher.network.services

import com.jeanporcher.tpjeanporcher.user.UserInfo
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("users/info")
    suspend fun getInfo(): Response<UserInfo>
}