package com.jeanporcher.tpjeanporcher.userinfo

import com.jeanporcher.tpjeanporcher.network.Api
import okhttp3.MultipartBody

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
}