package com.example.app_sophos.model

data class DataDocuments (
    val Items: List<DataListDocument>?,
    val Count:Int,
    val ScannedCount:Int
  ){
    constructor() : this( null, 0, 0)
}