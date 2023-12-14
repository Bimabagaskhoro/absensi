package com.febyputri.absensiapp.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

suspend fun <T> safeApiCall(
    dispatcher: CoroutineContext = Dispatchers.IO,
    apiCall: suspend () -> T?
): T {
    return withContext(dispatcher) {
        try {
            apiCall() ?: throw Exception("Network null")
        } catch (error: Throwable) {
            throw error
        }
    }
}
