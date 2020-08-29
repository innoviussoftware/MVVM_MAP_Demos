package com.example.mvvm_map_demos.data.network

import com.example.mvvm_map_demos.network.WebFields
import com.example.mvvm_map_demos.utils.ApiExceptions
import com.example.mvvm_map_demos.utils.ApiUnauthorizedExceptions
import com.example.mvvm_map_demos.utils.Applog
import com.example.mvvm_map_demos.utils.GlobalValues
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeAPIRequest {

    //Todo: After api call completed get success and faild data. And return responce data

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()

        //Todo: APi call response Check Success code ==200 ,Success data any comman data
        Applog.E("responce code::=> "+response.code())
        if (response.code() == GlobalValues.Success_Code) {
            return response.body()!!
        } else if (response.code() == GlobalValues.Unauthorized_Code) {
            //Todo: APi call response Check failer data with failer code
            val error = response.errorBody()?.string()

            val message = StringBuffer()
            error.let {
                try {
                    message.append(JSONObject(it).getString(WebFields.RESPONSE_ERRORS))
                } catch (e: JSONException) {

                }

                throw ApiUnauthorizedExceptions(message.toString())
            }
        }  else if (response.code() == GlobalValues.Internal_Server_Code) {
            //Todo: APi call response Check failer data with failer code
            val error = response.errorBody()?.string()
            // return response.errorBody()!!

            val message = StringBuffer()
            error.let {
                try {
                    message.append(JSONObject(it).getString(WebFields.RESPONSE_ERRORS))
                } catch (e: JSONException) {

                }
                /* message.append("\n")
                 message.append("Error code ${response.code()}")*/
                throw ApiExceptions(message.toString())
            }
        }else if (response.code() == GlobalValues.Not_Data_Found_Code) {
            //Todo: APi call response Check failer data with failer code
            val error = response.errorBody()?.string()
            // return response.errorBody()!!

            val message = StringBuffer()
            error.let {
                try {
                    message.append(JSONObject(it).getString(WebFields.RESPONSE_ERRORS))
                } catch (e: JSONException) {

                }
                /* message.append("\n")
                 message.append("Error code ${response.code()}")*/
                throw ApiExceptions(message.toString())
            }
        }else {
            //Todo: APi call response Check failer data with failer code
            val error = response.errorBody()?.string()
            // return response.errorBody()!!

            val message = StringBuffer()
            error.let {
                try {
                    message.append(JSONObject(it).getString(WebFields.RESPONSE_ERRORS))
                } catch (e: JSONException) {

                }
                /* message.append("\n")
                 message.append("Error code ${response.code()}")*/
                throw ApiExceptions(message.toString())
            }

        }
    }
}