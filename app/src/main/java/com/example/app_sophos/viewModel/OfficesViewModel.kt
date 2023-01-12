package com.example.app_sophos.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app_sophos.api.APIInteface
import com.example.app_sophos.api.RetrofitHelper
import com.example.app_sophos.model.DataListOffices
import com.example.app_sophos.model.DataOffices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OfficesViewModel : ViewModel() {
    private val officesViewModel = MutableLiveData<DataListOffices>()
    private val officeViewModel = MutableLiveData<DataOffices>()

    fun getOfficesViewModelObserver():MutableLiveData<DataListOffices>{
        return officesViewModel
    }

    fun getOfficeViewModelObserver():MutableLiveData<DataOffices>{
        return officeViewModel
    }

    fun getOffices(city:String?){

        val retrofit = RetrofitHelper.buildService(APIInteface::class.java)

        if (city.isNullOrEmpty()){
            //We will retrive all offices in all cities
            retrofit.requestAllOffices().enqueue(
                object :Callback<DataListOffices>{
                    override fun onResponse(call: Call<DataListOffices>, response: Response<DataListOffices>) {
                       if (response.code() == 400 ){
                           officesViewModel.postValue(DataListOffices())
                       }
                        else officesViewModel.postValue(response.body())
                    }

                    override fun onFailure(call: Call<DataListOffices>, t: Throwable) {
                        //TODO("Not yet implemented")
                    }
                }
            )
        }else{
            // We will retrive the specific office
            retrofit.requestOffice(city).enqueue(
                object :Callback<DataOffices>{
                    override fun onResponse(call: Call<DataOffices>, response: Response<DataOffices>) {
                        if (response.code() == 400 ){
                            officeViewModel.postValue(DataOffices())
                        }
                        else officeViewModel.postValue(response.body())
                    }

                    override fun onFailure(call: Call<DataOffices>, t: Throwable) {
                        //TODO("Not yet implemented")
                    }
                }
            )
        }

    }

}
