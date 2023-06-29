package com.example.mynewsapp.models

import com.example.mynewsapp.TopLinks


data class ApiResponse(
    val total_clicks: Int,
    val total_links: Int,
    var top_source: String ?=null ,
    var top_location: String ?=null,
    val data: Data,


)

data class Data(
    val overall_url_chart: Map<String, Int>,
    val recent_links: List<RecentLink>,
    val top_links: List<TopLink>
)

data class RecentLink(
    val smart_link: String,
    val app: String,
    val total_clicks: Int,
    val times_ago: String
)

data class TopLink(
    val smart_link: String,
    val app: String,
    val total_clicks: Int,
    val times_ago: String
)
