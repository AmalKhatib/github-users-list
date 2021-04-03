package com.example.githubusers.data.repositories

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.githubusers.data.db.AppDatabase
import com.example.githubusers.data.db.entities.User
import com.example.githubusers.data.network.ApiInterface
import com.example.githubusers.data.network.SafeApiRequest
import com.example.githubusers.data.network.responses.UsersResponse

class UserRepository(application: Application, private val api: ApiInterface)
    : SafeApiRequest() {

    //db is an abstract class that cannot be injected, so it is initiated using singleton
    private val appDatabase = AppDatabase.getDatabase(application)

    //local db queries invoke ////////
    suspend fun getLocalUsers() :ArrayList<User>{
        return appDatabase.getUserDao().getUsers() as ArrayList<User>
    }

    suspend fun insertUserToLocal(user: User){
        appDatabase.getUserDao().addUser(user = user)
    }

    suspend fun getUserFromLocal(id: Int) : User{
        return appDatabase.getUserDao().getUser(id = id)
    }
    //end local db queries invoke ////////

    //api requests calls //////
    suspend fun getUsersOnline() : UsersResponse {
        return apiRequest { api.getUsers() }
    }

    suspend fun getUserOnline(url: String) : User{
        return apiRequest { api.getUser(userUrl = url) }
    }
    //end api requests calls //////
}