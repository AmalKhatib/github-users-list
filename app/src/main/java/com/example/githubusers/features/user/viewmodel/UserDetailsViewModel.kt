package com.example.githubusers.features.user.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.db.entities.User
import com.example.githubusers.data.repositories.UserRepository
import com.example.githubusers.data.utils.Coroutines
import com.example.githubusers.util.Listener

class UserDetailsViewModel(private val userRepository: UserRepository, private val application: Application): ViewModel() {

    private lateinit var user: User
    var listener: Listener? = null

    fun getUserDetails(id: Int?){
        Coroutines.main {
            user = userRepository.getUserFromLocal(id!!)
            listener?.onSuccess(user)
            return@main
        }

    }
}