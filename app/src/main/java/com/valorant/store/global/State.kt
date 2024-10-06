package com.valorant.store.global

sealed class State<out T> {
    data object Loading : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
    data class Error(val exception: Throwable) : State<Nothing>()

    companion object {
        inline fun <reified T> of(action: () -> Any): State<T> = when (val value = action()) {
            is Throwable -> Error(value)
            is T -> Success(value)
            else -> Error(Exception("Wrong ClassParameter for UiState"))
        }

        inline fun <reified T : Any> of(result: Result<T>): State<T> =
            of { result.getOrElse { it } }
    }

    fun asResult(): Result<T> = when (this) {
        is Success -> Result.success(data)
        is Error -> Result.failure(exception)
        Loading -> TODO()
    }
}
