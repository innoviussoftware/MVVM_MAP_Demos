package com.example.mvvm_map_demos.utils

import kotlinx.coroutines.*

fun <T> lazyDeferred(block : suspend CoroutineScope.() -> T) : Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async (start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}