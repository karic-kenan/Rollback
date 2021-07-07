package io.aethibo.rollback.framework.utils

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Resource.Failure(e.localizedMessage)
    }
}