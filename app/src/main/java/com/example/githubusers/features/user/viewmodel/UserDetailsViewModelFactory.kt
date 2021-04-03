package com.example.githubusers.features.user.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubusers.data.repositories.UserRepository

class UserDetailsViewModelFactory(private val application: Application, private val userRepository: UserRepository)
    : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return UserDetailsViewModel(userRepository, application) as T
    }
}