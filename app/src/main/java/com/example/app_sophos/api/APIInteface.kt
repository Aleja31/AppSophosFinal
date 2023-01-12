package com.example.app_sophos.api

import com.example.app_sophos.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface APIInteface {

        @GET("RS_Usuarios")
        fun requestLogin(
                @Query("idUsuario") idUsuario:String,
                @Query("clave") clave:String
        ) : Call<DataUser>

        @GET("RS_Oficinas")
        fun requestAllOffices() : Call<DataListOffices>

        @GET("RS_Oficinas")
        fun requestOffice( @Query("ciudad") ciudad:String) : Call<DataOffices>

        @GET("RS_Documentos")
        fun requestDocumentById(@Query ("idRegistro") idRegistro:String) : Call<DataDocuments>

        @GET("RS_Documentos")
        fun requestAllDocuments(@Query ("correo") correo:String) : Call<DataDocuments>

        @POST("RS_Documentos")
        fun requestPostDocuments(@Body dataListDocument :DataDocumentsPost ) : Call<ResponseDocuments>
}