package com.valorant.store.data.datasource.user.service

import com.valorant.store.data.datasource.Client
import com.valorant.store.data.model.user.UserRemote
import retrofit2.Response
import retrofit2.http.GET

interface UserClient : Client {

    @GET("/userinfo")
    suspend fun userInfo(): Response<UserRemote>
}
