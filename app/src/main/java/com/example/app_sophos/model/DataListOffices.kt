package com.example.app_sophos.model

data class DataListOffices(
    val Items: List<DataOffices>?,
    val Count: Int,
    val ScannedCount: Int
) {
    constructor() : this( null, 0, 0)
}
