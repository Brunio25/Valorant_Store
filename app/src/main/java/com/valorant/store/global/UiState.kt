package com.valorant.store.global

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val exception: Throwable) : UiState<Nothing>()

    companion object {
        inline fun <reified T> of(action: () -> Any): UiState<T> = when (val value = action()) {
            is Throwable -> Error(value)
            is T -> Success(value)
            else -> Error(Exception("Wrong ClassParameter for UiState"))
        }

        inline fun <reified T> of(result: Result<T>): UiState<T> =
            when (val value = result.getOrElse { it }) {
                is Throwable -> Error(value)
                is T -> Success(value)
                else -> Error(Exception("Wrong ClassParameter for UiState"))
            }
    }
}
