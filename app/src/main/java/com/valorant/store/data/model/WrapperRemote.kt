package com.valorant.store.data.model

interface WrapperRemote<T> {
    val status: Int
    val data: T
}
