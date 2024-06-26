package com.valorant.store.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valorant.store.api.user.dto.UserInfoDTO
import com.valorant.store.api.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//class UserViewModel : ViewModel() {
//    private val userRepository: UserRepository = UserRepository
//
//    private val _userInfo = MutableStateFlow<Result<UserInfoDTO>?>(null)
//    val userInfo: StateFlow<Result<UserInfoDTO>?> = _userInfo
//
//    fun getUserInfo() {
//        viewModelScope.launch {
//            val res = userRepository.getUserInfo()
//            _userInfo.value = res
//            Log.i("REPOSITORY", _userInfo.value.toString())
//        }
//    }
//}
