package com.example.githubusers.data.network

import com.example.githubusers.data.db.entities.User
import com.example.githubusers.data.network.responses.UsersResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {

    @GET("users?since=0")
    suspend fun getUsers() : Response<UsersResponse>

    @GET
    suspend fun getUser(@Url userUrl: String) : Response<User>

    companion object{
        operator fun invoke()
                    : ApiInterface{

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val okkHttpclient = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiInterface::class.java)
        }

    }

}