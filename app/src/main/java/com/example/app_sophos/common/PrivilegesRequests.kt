package com.example.app_sophos.common

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.app_sophos.R
import com.example.app_sophos.view.FragmentCamera

class PrivilegesRequests(activity : AppCompatActivity, permissionCode: Int, permissionType: String) : AppCompatActivity(){
    private val context: AppCompatActivity = activity
    private val permissionCode: Int = permissionCode
    private val permission : String = permissionType
    private var responseAccess : Boolean =  false


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        startCheckingPermission()

    }

     fun startCheckingPermission(): Boolean{
         checkPermission()
         return responseAccess
     }

     fun checkPermission(){
        if (ContextCompat.checkSelfPermission(context, permission!!)!= PackageManager.PERMISSION_GRANTED) {
            requestPermission()
        }else{
            responseAccess = true
        }

    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission!!)) {
            Toast.makeText(context, R.string.msg_no_privileges_eng, Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(context, arrayOf(permission), permissionCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionCode){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                responseAccess = true
            }else{
                Toast.makeText(context, R.string.msg_no_privileges_eng, Toast.LENGTH_SHORT).show()

            }
        }
    }
}