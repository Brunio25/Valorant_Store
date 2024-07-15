package com.valorant.store.api.essential_data.user

import com.valorant.store.api.Api
import com.valorant.store.api.essential_data.user.dto.UserInfoDTO
import retrofit2.Response
import retrofit2.http.GET

interface UserApi : Api {

    @GET("/userinfo")
    suspend fun userInfo(): Response<UserInfoDTO>
}
