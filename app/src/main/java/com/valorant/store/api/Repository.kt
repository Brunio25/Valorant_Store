package com.valorant.store.api

abstract class Repository<T : Api>(apiClass: Class<T>) {
    protected abstract val baseUrl: String
    protected val apiClient: T by lazy { ClientProvider.getInstance(apiClass, baseUrl) }
}
