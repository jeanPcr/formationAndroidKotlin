package com.jeanporcher.tpjeanporcher.userinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody

class UserInfoViewModel: ViewModel() {
    private val userInfoRepository = UserInfoRepository()
    private val _userInfos = MutableLiveData<UserInfo>()
    val userInfos: LiveData<UserInfo> = _userInfos

    suspend fun getInfos() {
        val infos = userInfoRepository.getInfos()
        _userInfos.value = infos!!
    }
    suspend fun updateAvatar(avatar: MultipartBody.Part){
        val infos = userInfoRepository.updateAvatar(avatar)
        if (infos!= null) {
            _userInfos.value = infos
        }
    }
    suspend fun updateUser(user: UserInfo){
        val infos = userInfoRepository.updateUser(user)
        if (infos!= null) {
            _userInfos.value = infos
        }
    }
}