package com.stocard.core.network

/**
 * Created By Rafiqul Hasan
 */
sealed class Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>()
    class Error(val exception: Throwable, val code: Int) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}