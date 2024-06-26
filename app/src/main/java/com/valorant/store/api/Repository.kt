package com.valorant.store.api

open class Repository<T : Api>(apiClass: Class<T>, baseUrl: String) {
    protected val apiClient: T = ClientProvider.getInstance(apiClass, baseUrl)
}
