package com.example.mynewsapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynewsapp.models.ApiResponse
import com.example.mynewsapp.models.RecentLink
import com.example.mynewsapp.models.TopLink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainViewModel : ViewModel() {
    private val BASE_URL = "https://api.inopenapp.com/"
    private val API_TOKEN =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"
    val totalClicks = MutableLiveData<Int>()
    val totalLinks = MutableLiveData<Int>()
    val topSource = MutableLiveData<String>()
    val topLocation = MutableLiveData<String>()
    val overallUrlChart = MutableLiveData<Map<String, Int>>()
    val recentLinks = MutableLiveData<List<RecentLink>>()
    val topLinks = MutableLiveData<List<TopLink>>()
    val error = MutableLiveData<String>()



    fun fetchData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getData("Bearer $API_TOKEN")

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    totalClicks.value = apiResponse?.total_clicks ?: 0
                    totalLinks.value = apiResponse?.total_links ?: 0
                    topSource.value = apiResponse?.top_source ?: ""
                    topLocation.value = apiResponse?.top_location ?: ""
                    overallUrlChart.value = apiResponse?.data?.overall_url_chart
                    val linkDataList = apiResponse?.data?.top_links?: emptyList()
                    if (linkDataList != null) {
                        topLinks.value = linkDataList
                    } else {
                        error.value = "No top links found."
                    }
                    val linkDataList2 = apiResponse?.data?.recent_links?: emptyList()
                    if (linkDataList2 != null) {
                        recentLinks.value = linkDataList2
                    } else {
                        error.value = "No top links found."
                    }
                }
                else {
                    error.value = "API request failed with code ${response.code()}"
                }

            }



            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {

            }
        })
    }
}