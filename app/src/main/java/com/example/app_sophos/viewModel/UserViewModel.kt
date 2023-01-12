package com.example.app_sophos.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app_sophos.api.APIInteface
import com.example.app_sophos.api.RetrofitHelper
import com.example.app_sophos.model.DataUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel () {

    private val userModel = MutableLiveData<DataUser>()
    private val isLoading = MutableLiveData<Boolean>()

    fun getUserViewModelObserver():MutableLiveData<DataUser>{
        return userModel
    }

    fun getIsLoadingObserver():MutableLiveData<Boolean>{
        return isLoading
    }

    fun login(username:String,password:String){

        isLoading.postValue(true)

        val retrofit = RetrofitHelper.buildService(APIInteface::class.java)

        retrofit.requestLogin(username,password).enqueue(
            object :Callback<DataUser>{
                override fun onResponse(call: Call<DataUser>, response: Response<DataUser>) {
                    if (response.code() == 400){
                        userModel.postValue(DataUser())
                    }else userModel.postValue(response.body())

                    isLoading.postValue(false)
                }
                override fun onFailure(call: Call<DataUser>, t: Throwable) {
                    call.cancel()
                }
            }
        )

    }
}


