package com.example.chatapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchDataFromApi()
    }

    private fun fetchDataFromApi() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://verify.infraveo.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getData()

        call.enqueue(object : Callback<List<ChatItem>> {
            override fun onResponse(call: Call<List<ChatItem>>, response: Response<List<ChatItem>>) {
                if (response.isSuccessful) {
                    val chatItemList = response.body()
                    if (chatItemList != null) {
                        chatAdapter = ChatAdapter(chatItemList)
                        recyclerView.adapter = chatAdapter
                    }
                } else {
                    Toast.makeText(this@MainActivity, "API call failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ChatItem>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "API call failed", Toast.LENGTH_SHORT).show()
            }
        })
    }
}