package com.example.app_sophos.common

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.example.app_sophos.model.Preferences
import java.io.ByteArrayOutputStream


class GeneralActions : Application () {
    companion object {
        lateinit var prefer : Preferences
    }

    override fun onCreate() {
        super.onCreate()
        prefer = Preferences(applicationContext)
    }

    //Function to convert image from bitmap to Base64
    fun convertImageToBase64(image: Bitmap?) : String{
        val stream = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.JPEG, 100,stream)
        val finalImage = stream.toByteArray()
        return android.util.Base64.encodeToString(finalImage,android.util.Base64.DEFAULT)
    }

    //Function to convert image from Uri to Base64
    fun convertUriToBitmap(context: Context,uri: Uri?) : Bitmap {
        lateinit var imageBase64: Bitmap
        if(Build.VERSION.SDK_INT < 28){
            imageBase64 = MediaStore.Images.Media.getBitmap(context.contentResolver,uri)
        }else {
            val source = ImageDecoder.createSource(context.contentResolver,uri!!)
            imageBase64 = ImageDecoder.decodeBitmap(source)
        }
        return imageBase64
    }
}