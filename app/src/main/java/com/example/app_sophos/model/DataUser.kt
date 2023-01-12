package com.example.app_sophos.model
//This class contains the User data which we will get from the API accordingly to the API definition
/*
{
    "id": "125",
    "nombre": "María Alejandra",
    "apellido": "Rojas Escobar",
    "acceso": true,
    "admin": false
}
*/
 data class DataUser (
    val id:String,
    val nombre:String,
    val apellido:String,
    val acceso:Boolean,
    val admin:Boolean ){

    constructor() :this ("",
                "",
                "",
                false,
               false)
}