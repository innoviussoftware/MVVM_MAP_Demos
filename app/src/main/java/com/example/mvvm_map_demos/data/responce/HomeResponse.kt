package com.example.mvvm_map_demos.data.responce

data class HomeResponse(
    val ad: Ad,
    val `data`: List<Data>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)

data class Ad(
    val company: String,
    val text: String,
    val url: String
)

data class Data(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val avatar: String?=null,
    val email: String
)