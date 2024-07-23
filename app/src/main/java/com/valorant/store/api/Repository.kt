package com.valorant.store.api

interface Api

abstract class Repository<T : Api>(apiClass: Class<T>, useAuth: Boolean = true) {
    protected abstract val baseUrl: String
    protected val apiClient: T by lazy { ClientProvider.getInstance(apiClass, baseUrl, useAuth) }
}
