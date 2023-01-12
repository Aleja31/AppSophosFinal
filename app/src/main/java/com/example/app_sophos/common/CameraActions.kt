package com.example.app_sophos.common
//
//import android.app.Activity
//import android.app.Application
//import android.content.Intent
//import android.graphics.Bitmap
//import android.graphics.ImageDecoder
//import android.net.Uri
//import android.os.Build
//import android.provider.MediaStore
//import androidx.core.app.ActivityCompat.startActivityForResult
//import androidx.core.os.bundleOf
//import com.example.app_sophos.R
//import com.example.app_sophos.view.SendDocumentsActivity
//import java.io.ByteArrayOutputStream
//
//class CameraActions (activity: Activity) : Application() {
//    val activity: Activity = activity
//    var image: String = ""
//
//    fun choosePicture(): String{
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        //startActivityForResult()
//        activity.startActivityForResult(intent, R.string.code_select_photo)
//        println("imageennnn: $image")
//        return image
//    }
//
//     override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//         println("onActivityResult-> $resultCode")
//        if(resultCode == Activity.RESULT_OK){
//            when (requestCode) {
//                R.string.code_camera_permission-> {
//                    data ?. extras ?. let {
//                        val imageBitmap = it.get("data") as Bitmap
//                        //listener(convertImageToBase64(imageBitmap))
//                        image = convertImageToBase64(imageBitmap)
//                        println("imageennnn2222: $image")
//                        //dismiss()
//                    }
//                }
//                R.string.code_select_photo ->{
//                    val imageBitmap = convertUtiToBase64(data?.data)
//                    //listener(convertImageToBase64(imageBitmap))
//                    image = convertImageToBase64(imageBitmap)
//                    println("imageennnn33333: $image")
//                    //dismiss()
//                }
//            }
//        }
//        else {
//            //listener(resultCode.toString())
//            image = resultCode.toString()
//            //dismiss()
//        }
//    }
//
//    fun convertImageToBase64(image: Bitmap?) : String{
//        val stream = ByteArrayOutputStream()
//        image?.compress(Bitmap.CompressFormat.JPEG, 100,stream)
//        val finalImage = stream.toByteArray()
//        return android.util.Base64.encodeToString(finalImage,android.util.Base64.DEFAULT)
//    }
//
//    fun convertUtiToBase64(uri: Uri?) : Bitmap{
//        lateinit var imageBase64: Bitmap
//        if(Build.VERSION.SDK_INT < 28){
//            imageBase64 = MediaStore.Images.Media.getBitmap(baseContext.contentResolver,uri)
//        }else {
//            val source = ImageDecoder.createSource(baseContext.contentResolver,uri!!)
//            imageBase64 = ImageDecoder.decodeBitmap(source)
//        }
//        return imageBase64
//    }
//}