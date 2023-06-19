package com.example.chatapplication

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("userlist.php")
    fun getData(): Call<List<ChatItem>>
}