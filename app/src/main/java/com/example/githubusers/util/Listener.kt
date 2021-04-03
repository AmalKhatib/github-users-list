package com.example.githubusers.util

interface Listener {
    fun onFailure(message: String)
    fun onSuccess(user: Any?)
}