package com.maeiapp.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_map_demos.data.repository.HomeRepository
import com.example.mvvm_map_demos.data.responce.HomeResponse
import com.example.mvvm_map_demos.utils.ApiExceptions
import com.example.mvvm_map_demos.utils.Coroutines
import com.example.mvvm_map_demos.utils.NoInternetException

//Todo: implement View Model it is compulsory as per ViewModel class Life cycle
class HomeViewModel(private val userRepository: HomeRepository) : ViewModel() {

    var authData: MutableLiveData<HomeResponse>? = null

    var apiResponse: HomeResponse? = null

    //Todo: Get activity thru data Send request param in api using Live data.
    // MutableLiveData using stored data in view
    fun callHomeDetailsAPI(context: Context, page:Int): LiveData<HomeResponse> {

        authData = MutableLiveData<HomeResponse>()

        Coroutines.main {
            try {
                apiResponse = userRepository.callHomeDetailsAPI(page)
                authData!!.value = apiResponse

            } catch (e: ApiExceptions) {
                  authData!!.value =null

            } catch (e: NoInternetException) {
              //  context.toast("Check your internet connection...")
            }
        }
        return authData!!
    }


}