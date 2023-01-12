package com.example.app_sophos.view


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.app_sophos.R
import com.example.app_sophos.common.GeneralActions
import com.example.app_sophos.databinding.FragmentCameraBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


//import kotlinx.android.synthetic.main.fragment_camera.view.*


class FragmentCamera (val listener : (String) -> Unit): DialogFragment () {
    private val generalActions :GeneralActions = GeneralActions()
    private var fragmentCamera: FragmentCameraBinding?=null
    private val binding get() = fragmentCamera!!

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        saveInstanceState: Bundle?
    ): View? {
        fragmentCamera = FragmentCameraBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTakePhoto.setOnClickListener {
            takePicture()
        }

        binding.btnChoosePhoto.setOnClickListener {
            choosePicture()
        }
    }

    private fun takePicture(){
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,R.string.code_camera_permission)
    }

    fun choosePicture(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,R.string.code_select_photo)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                R.string.code_camera_permission-> {
                    data ?. extras ?. let {
                        val imageBitmap = it.get("data") as Bitmap
                        listener(generalActions.convertImageToBase64(imageBitmap))
                        dismiss()
                        }
                }
                R.string.code_select_photo ->{

                    // 1. Convert uri to bitmap
                    val imageBitmap = generalActions.convertUriToBitmap(requireContext(),data?.data)
                    // 2. Get the downsized image content as a byte[]
                    val scaleWidth = imageBitmap.width / 10
                    val scaleHeight = imageBitmap.height / 10

                    val scaledImage = Bitmap.createScaledBitmap(imageBitmap,scaleWidth,scaleHeight,true)
                    var out  = ByteArrayOutputStream()
                    scaledImage.compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 0, out)
                    val array = out!!.toByteArray()
                    val image64 = Base64.encodeToString(array, Base64.DEFAULT)

                    listener(image64)
                    dismiss()
                }
            }
        }
        else {
            listener(resultCode.toString())
            dismiss()
        }
    }

}