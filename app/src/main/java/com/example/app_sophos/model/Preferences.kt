package com.example.app_sophos.model

import android.content.Context

class Preferences (val context: Context){
    private val SHARE_USER_NAME = "userName"
    private val SHARE_PASSWORD = "password"
    private val SHARE_NAME = "name"
    private val NAME_PREFERENCES = "myPreferences"
    //mode 0 means Private
    private val storage = context.getSharedPreferences(NAME_PREFERENCES,0)

    fun getDataLogin ():MutableList<String>{
       val userName = storage.getString(SHARE_USER_NAME, "")!!
        val password = storage.getString(SHARE_PASSWORD, "")!!
        var  list : MutableList<String> = arrayListOf()
        list.addAll(listOf(userName,password))
        return  list
    }
    fun setDataLogin (userName: String, password: String){
        storage.edit().putString(SHARE_USER_NAME,userName).apply()
        storage.edit().putString(SHARE_PASSWORD, password).apply()
    }

    fun getDataName (): String {
        val name = storage.getString(SHARE_NAME, "")!!
        return name
    }

    fun setDataName (name : String){
        storage.edit().putString(SHARE_NAME,name).apply()
    }
    fun getEmail (): String {
        val email = storage.getString(SHARE_USER_NAME, "")!!
        return email
    }
}