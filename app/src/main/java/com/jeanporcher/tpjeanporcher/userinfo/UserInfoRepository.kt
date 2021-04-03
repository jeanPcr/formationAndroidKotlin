package com.jeanporcher.tpjeanporcher.userinfo

import com.jeanporcher.tpjeanporcher.authentication.login.LoginForm
import com.jeanporcher.tpjeanporcher.authentication.login.LoginResponse
import com.jeanporcher.tpjeanporcher.network.Api
import okhttp3.MultipartBody
import okhttp3.Response

class UserInfoRepository {
    private val userWebService = Api.userService

    suspend fun getInfos(): UserInfo?{
        val userInfos = userWebService.getInfo()
        return if (userInfos.isSuccessful) userInfos.body()!! else null
    }
    suspend fun updateAvatar(avatar: MultipartBody.Part): UserInfo?{
        val userInfos = userWebService.updateAvatar(avatar)
        return if (userInfos.isSuccessful) userInfos.body()!! else null
    }
    suspend fun updateUser(user: UserInfo): UserInfo?{
        val userInfos = userWebService.update(user)
        return if (userInfos.isSuccessful) userInfos.body()!! else null
    }
    suspend fun login(loginForm: LoginForm): Boolean {
        val loginReponse = userWebService.login(loginForm)
        if (loginReponse.isSuccessful) return true
        return false
    }
}