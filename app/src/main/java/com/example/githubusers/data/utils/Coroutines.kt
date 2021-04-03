package com.example.githubusers.data.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Cotoutines applied for executing tasks asynchronously.
object Coroutines {
    fun main(work: suspend (() -> Unit)) =
        /*
        * CoroutineScope for Api calls (suspend functions).
        * Coroutine Dispatchers.Main,
        * to run a coroutine on the main Android thread.
        * This is used  for interacting with the UI and performing quick work.
        * mainly for including calling suspend functions.
        * */
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }
}