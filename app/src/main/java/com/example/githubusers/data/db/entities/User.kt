package com.example.githubusers.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* Database entity (table)*/
@Entity(tableName = "User")
data class User(
    val avatar_url: String,

    val bio: String?,

    val blog: String?,

    val company: String?,

    val email: String?,

    val followers: Int,
    val following: Int,
    val html_url: String?,
    @PrimaryKey val id: Int,

    val location: String?,

    val login: String?,
    val name: String?,
    val public_gists: Int,
    val public_repos: Int,
   val twitter_username: String?,
    val updated_at: String?,
    val url: String?
)