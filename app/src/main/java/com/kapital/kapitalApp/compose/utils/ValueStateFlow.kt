package com.kapital.kapitalApp.compose.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class ValueStateFlow<T, R> private constructor(val initial: T? = null) {

    data class WrapperValue<T>(val value: T?, val isInitial: Boolean)

    private val emptyValue = WrapperValue<T>(null, isInitial = true)

    @Suppress("TooGenericExceptionThrown")
    private val _valueState by lazy {
        MutableStateFlow(initial?.let { WrapperValue(it, true) } ?: emptyValue)
            .listenChanges(
                scope = scope ?: throw Exception("invalid scope"),
                map = {
                    if (it != emptyValue) {
                        map(it.value)
                    }
                },
                onError = {
                    error.value = onError(it)
                }
            )
    }
    private var scope: CoroutineScope? = null
    private lateinit var map: (T?) -> Unit
    private lateinit var onError: (Throwable?) -> R
    private lateinit var initialError: () -> R
    private var isUpdatable: (T?) -> Boolean = { true }

    @Suppress("TooGenericExceptionThrown")
    val valueState: StateFlow<T?> by lazy {
        _valueState.map { it.value }.stateIn(
            scope ?: throw Exception("Invalid scope"),
            SharingStarted.WhileSubscribed(),
            null
        )
    }
    val error by lazy { MutableStateFlow<R>(initialError()) }
    fun update(value: T?) {
        if (isUpdatable(value)) {
            _valueState.value = WrapperValue(value, false)
        }
    }

    suspend fun getValue(): T? {
        return coroutineScope {//withContext(Dispatchers.Default) {
            _valueState.first().value
        }
    }

    companion object {
        fun <T, R> createValueState(
            initial: T? = null,
            config: Build<T, R>.() -> Unit
        ): ValueStateFlow<T, R> {
            val valueState = ValueStateFlow<T, R>(initial)
            config(Build(valueState))
            return valueState
        }
    }

    class Build<T, R>(private val valueScope: ValueStateFlow<T, R>) {
        fun addScope(scope: CoroutineScope) {
            valueScope.scope = scope
        }

        fun addTransformCheckValue(map: (T?) -> Unit) {
            valueScope.map = map
        }

        fun addOnError(error: (Throwable?) -> R, initialError: () -> R) {
            valueScope.onError = error
            valueScope.initialError = initialError
        }

        fun addUpdatable(isUpdatable: (T?) -> Boolean) {
            valueScope.isUpdatable = isUpdatable
        }
    }
}

fun <T> MutableStateFlow<T>.listenChanges(
    scope: CoroutineScope,
    map: (T) -> Unit,
    onError: (Throwable?) -> Unit
): MutableStateFlow<T> {
    onEach {
        kotlin.runCatching {
            map(it)
        }.exceptionOrNull()?.let { onError(it) } ?: onError(null)
    }.launchIn(scope)
    return this
}
