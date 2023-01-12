package com.example.app_sophos.model

data class DataListDocument (
    var IdRegistro: String,
    var Fecha:String,
    var TipoId:String,
    var Identificacion:String,
    var Nombre:String,
    var Apellido:String,
    var Ciudad:String,
    var TipoAdjunto:String,
    var Adjunto:String,
    var Correo: String?,
) {
    constructor() : this("", "", "","","","","","","","")


}