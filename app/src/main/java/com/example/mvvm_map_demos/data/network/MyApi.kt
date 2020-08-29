package com.example.mvvm_map_demos.network


import com.example.mvvm_map_demos.data.responce.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    //Todo: Common Used... Start.............................>>>

    @GET(WebFields.REQEST_USERS)
    suspend fun callHomeDetails(
        @Query(WebFields.REQUEST_PAGE)  id: Int?
    ): Response<HomeResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {

            val okHttpClient = OkHttpClient.Builder().addInterceptor(networkConnectionInterceptor)
                .build() // Used to check internet connections.

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(WebFields.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}