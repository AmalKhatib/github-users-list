package com.example.githubusers.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.githubusers.data.db.entities.User

/*
* Database operations*/
@Dao
interface UserDao {

    @Query("SELECT * FROM USER")
    suspend fun getUsers() : List<User>

    @Insert
    suspend fun addUser(user: User)

    @Query("SELECT * FROM USER WHERE id = :id")
    suspend fun getUser(id: Int) : User
}