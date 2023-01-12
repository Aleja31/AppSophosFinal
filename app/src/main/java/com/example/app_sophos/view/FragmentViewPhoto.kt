package com.example.app_sophos.view

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.app_sophos.databinding.FragmentCameraBinding
import com.example.app_sophos.databinding.FragmentViewPhotoBinding

class FragmentViewPhoto(image: Bitmap): DialogFragment() {
//class FragmentViewPhoto(image: String): DialogFragment() {
    private val image = image
    private var fragmentViewPhoto: FragmentViewPhotoBinding?=null
    private val binding get() = fragmentViewPhoto!!

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        saveInstanceState: Bundle?
    ): View? {
        fragmentViewPhoto = FragmentViewPhotoBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageViewPhoto.setImageBitmap(image)
        binding.btnBack.setOnClickListener{
            dismiss()
        }

    }

}