package com.example.mynewsapp.model
import com.example.mynewsapp.models.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("api/v1/dashboardNew/")
    fun getData(@Header("Authorization") token: String): Call<ApiResponse>
}
