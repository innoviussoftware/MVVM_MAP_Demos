package com.example.mvvm_map_demos.data.repository

import com.example.mvvm_map_demos.data.network.SafeAPIRequest
import com.example.mvvm_map_demos.data.responce.HomeResponse
import com.example.mvvm_map_demos.network.MyApi

class HomeRepository(private val api: MyApi): SafeAPIRequest() {

    suspend fun callHomeDetailsAPI(page:Int): HomeResponse {
        //Todo: Api call Execute using request param, and return values "LoginResponse" as per set return type/data
        return apiRequest { api.callHomeDetails(page) }
    }



}