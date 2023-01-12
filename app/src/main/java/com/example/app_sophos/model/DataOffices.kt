package com.example.app_sophos.model

data class DataOffices(
    val IdOficina: Int?,
    val Nombre: String?,
    val Ciudad: String?,
    val Longitud: String?,
    val Latitud: String?
) {
    constructor() : this(null,null,null,null,null)
}
