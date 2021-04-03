package com.example.githubusers.data.network

import com.example.githubusers.data.utils.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

/*
* for Api requests
* */
abstract class SafeApiRequest {

    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T{
        val response = call.invoke()
        if(response.isSuccessful){
            return response.body()!!
        }else{
            val error = response.errorBody().toString()

            val message = StringBuilder()
            error?.let{
                try{
                    message.append(JSONObject(it).getString(response.code().toString() + "\n"+response.errorBody() + "\n"+response.message()))
                }catch(e: JSONException) {
                    message.append(e.message)
                }
            }
            throw ApiException(message.toString())
        }
    }

}