package com.example.app_sophos.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app_sophos.api.APIInteface
import com.example.app_sophos.api.RetrofitHelper
import com.example.app_sophos.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DocumentsViewModel  : ViewModel(){
    private val documentViewModel = MutableLiveData<DataDocuments>()
    private val documentsViewModel = MutableLiveData<DataDocuments>()
    private val postDocumentsViewModel = MutableLiveData<ResponseDocuments>()

    fun getDocumentViewModelObserver(): MutableLiveData<DataDocuments>{
        return documentViewModel
    }

    fun getDocumentsViewModelObserver(): MutableLiveData<DataDocuments>{
        return documentsViewModel
    }

    fun getPostDocumentViewModel(): MutableLiveData<ResponseDocuments>{
        return postDocumentsViewModel
    }

    fun getDocuments(correo: String){
        val retrofit = RetrofitHelper.buildService(APIInteface::class.java)
        retrofit.requestAllDocuments(correo).enqueue(
            object : Callback<DataDocuments> {
                override fun onResponse(call: Call<DataDocuments>, response: Response<DataDocuments>) {
                    if (response.code() == 400 ){
                        documentsViewModel.postValue(DataDocuments())
                    }
                    else documentsViewModel.postValue(response.body())
                }

                override fun onFailure(call: Call<DataDocuments>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            }

        )

    }

    fun getDocument(id:String){
        val retrofit = RetrofitHelper.buildService(APIInteface::class.java)

        retrofit.requestDocumentById(id).enqueue(
            object : Callback<DataDocuments> {
                override fun onResponse(call: Call<DataDocuments>, response: Response<DataDocuments>) {
                    if (response.code() == 400 ){
                        documentViewModel.postValue(DataDocuments())
                    }
                    else documentViewModel.postValue(response.body())
                }

                override fun onFailure(call: Call<DataDocuments>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            }

        )
    }

    fun postDocument(dataListDocument: DataDocumentsPost){
        val retrofit = RetrofitHelper.buildService(APIInteface::class.java)

        retrofit.requestPostDocuments(dataListDocument).enqueue(
            object : Callback<ResponseDocuments>{
                override fun onResponse(call: Call<ResponseDocuments>, response: Response<ResponseDocuments>) {

                    if (response.code() == 400 ){
                        postDocumentsViewModel.postValue(ResponseDocuments())

                    }
                    else postDocumentsViewModel.postValue(response.body())
                }

                override fun onFailure(call: Call<ResponseDocuments>, t: Throwable) {
                    //TODO("Not yet implemented")
                    println(t.stackTrace.toString())

                }
            }

        )
    }
}
