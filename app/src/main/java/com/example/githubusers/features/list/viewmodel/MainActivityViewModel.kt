package com.example.githubusers.features.list.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.db.entities.User
import com.example.githubusers.data.repositories.UserRepository
import com.example.githubusers.data.utils.ApiException
import com.example.githubusers.data.utils.ConnectivityStatus
import com.example.githubusers.data.utils.Coroutines
import com.example.githubusers.util.Listener
import java.lang.Exception

class MainActivityViewModel(
    private val userRepository: UserRepository,
    private val application: Application
) : ViewModel() {

    var listener: Listener? = null

    fun getUsers() {
        Coroutines.main {
            val offlineUsers = userRepository.getLocalUsers()
            if (ConnectivityStatus.isOnline(application)) {
                try {
                    val usersOnline = userRepository.getUsersOnline()
                    usersOnline?.let {
                        if (offlineUsers.size == 0) {
                            //invoke each users’ required info then insert into local
                            for (item in usersOnline) {
                                /*
                                * the required info are not all provided from the list of users,
                                * so another call on each user’s url is done*/
                                val user = userRepository.getUserOnline(item.url)
                                userRepository.insertUserToLocal(user)
                            }
                        } else if (usersOnline.size > offlineUsers.size) { //new data available
                            //get the starting index of the new data
                            val index =
                                ((usersOnline.size) - (usersOnline.size - offlineUsers.size)) - 1

                            //insert the new data into local
                            for (i in index..usersOnline.size) {
                                val user = userRepository.getUserOnline(usersOnline[i].url)
                                userRepository.insertUserToLocal(user)
                            }
                        }
                    }
                } catch (e: ApiException) {
                    listener?.onFailure(e.message!!)
                } catch (e: Exception) {
                    listener?.onFailure(e.message!!)
                }
                listener?.onSuccess(userRepository.getLocalUsers())
                return@main
            }
            listener?.onSuccess(offlineUsers)
            return@main
        }

    }

}