package com.example.app_sophos.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.app_sophos.R
import com.example.app_sophos.common.GeneralActions
import com.example.app_sophos.common.PrivilegesRequests
import com.example.app_sophos.databinding.SendDocumentsBinding
import com.example.app_sophos.model.DataDocumentsPost
import com.example.app_sophos.model.DataListDocument
import com.example.app_sophos.model.DataOffices
import com.example.app_sophos.viewModel.DocumentsViewModel
import com.example.app_sophos.viewModel.OfficesViewModel
import java.io.ByteArrayOutputStream

class SendDocumentsActivity : Fragment() {
    private val generalActions :GeneralActions = GeneralActions()
    private var _binding: SendDocumentsBinding? = null
    private val sendDocumentsBinding get() = _binding!!
    val DataOfficesViewModel: OfficesViewModel by viewModels()
    private val DataDocumentsViewModel : DocumentsViewModel by viewModels()
    private var imageBase64 : String? = null
    private lateinit var infoPost : DataListDocument


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SendDocumentsBinding.inflate(inflater, container, false)
        return sendDocumentsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendDocumentsBinding.imagePhoto.setOnClickListener{

           val privilegeRequest = PrivilegesRequests(requireActivity() as AppCompatActivity, R.string.code_camera_permission, Manifest.permission.CAMERA)
            val hasPermission = privilegeRequest.startCheckingPermission()
            if (hasPermission){
                openCamera()
            }
        }
        //Makes the request to the API and generate the change in the data
        DataOfficesViewModel.getOffices(null)


        //Observer to check if there is any change on the DataModel
        DataOfficesViewModel.getOfficesViewModelObserver().observe(viewLifecycleOwner){
            if (it.Items.isNullOrEmpty()){
                Toast.makeText(requireContext(), "There were a problem with the server, please try again later", Toast.LENGTH_SHORT).show()
            }
            else{
                val cities = getCities(it.Items)
                loadSpinnerCities(cities)
            }
        }

        DataDocumentsViewModel.getPostDocumentViewModel().observe(viewLifecycleOwner){
            if (it.put){
                Toast.makeText(requireContext(), "Documentos enviados satisfactoriamente", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(requireContext(), "Ocurrió un problema", Toast.LENGTH_SHORT).show()
            }
        }

        loadSpinnerTypeDocuments()
        getEmail()

        sendDocumentsBinding.btnSend.setOnClickListener {

            if (validateFields()){
                val typeId = sendDocumentsBinding.spnElements.selectedItem.toString()
                val identification= sendDocumentsBinding.numberOfDocument.text.toString()
                val name = sendDocumentsBinding.textInputNameDocuments.editText?.text.toString()
                val lastName= sendDocumentsBinding.textInputLastnameDocuments.editText?.text.toString()
                val city = sendDocumentsBinding.spinnerCity.selectedItem.toString()
                val email = sendDocumentsBinding.editTextEmailDocuments.editText?.text.toString()
                val typeAttachment= sendDocumentsBinding.txtInTypeDocuments.text.toString()
                val image = imageBase64

                var documents = DataDocumentsPost (typeId,identification,name,lastName,city,email,typeAttachment,image!!)
                DataDocumentsViewModel.postDocument(documents)
            }
        }

        sendDocumentsBinding.btnUploadDocument.setOnClickListener {
            choosePicture()
        }
    }


    private fun loadSpinnerTypeDocuments(){
        val spinner = sendDocumentsBinding.spnElements//findViewById<Spinner>(sendDocumentsBinding.spnElements.id)
        val list = resources.getStringArray(R.array.TypeOfDocument)
        val adapter= ArrayAdapter(requireContext(), R.layout.style_spinner,list)
        spinner.adapter=adapter
    }

    private fun getEmail(){
        val  list : MutableList<String> = GeneralActions.prefer.getDataLogin()
        sendDocumentsBinding.editTextEmailDocuments.editText?.setText(list[0])
    }

    fun getCities( listcities: List<DataOffices>) : MutableList<String?> {
        var cities : MutableSet<String?> = mutableSetOf()
        cities.add("Ciudades")
        for ( i in listcities){
            cities.add(i.Ciudad)
        }
        return cities.toMutableList()
    }

    private fun loadSpinnerCities(cities : MutableList<String?>){
        val spinner = sendDocumentsBinding.spinnerCity
        val adapter= ArrayAdapter(requireContext(), R.layout.style_spinner,cities)
        spinner.adapter=adapter
    }

    private fun openCamera() {
        var dialog = FragmentCamera{onPhotoTaken(it)}
        dialog.show(getParentFragmentManager(),"customDialog")
    }

    private fun onPhotoTaken(photoBase64: String){
        if(photoBase64.length < 2){
            Toast.makeText(requireContext(), R.string.msg_error_taken_photo_spn,Toast.LENGTH_SHORT).show()
        }
        imageBase64 = photoBase64

    }

    fun validateFields(): Boolean{

        var validationResult = true
        val tipoId = sendDocumentsBinding.spnElements
        val identificacion= sendDocumentsBinding.numberOfDocument
        val nombre = sendDocumentsBinding.textInputNameDocuments
        val apellido= sendDocumentsBinding.textInputLastnameDocuments
        val ciudad = sendDocumentsBinding.spinnerCity
        val tipoAdjunto = sendDocumentsBinding.txtInTypeDocuments
        val adjunto = imageBase64
        val alert = sendDocumentsBinding.textViewAlert

        if (tipoId.selectedItemPosition == 0){
            alert.text="Por favor, seleccione su tipo de documento"
            validationResult = false
        }
        if (identificacion.text.toString().isNullOrEmpty()){
            alert.text="Por favor, ingrese su número de documento"
            validationResult = false
        }
        if (nombre.editText?.text.toString().isNullOrEmpty()){
            alert.text="Por favor, ingrese su nombre"
            validationResult = false
        }
        if (apellido.editText?.text.toString().isNullOrEmpty()){
            alert.text="Por favor, ingrese su apellido"
            validationResult = false
        }
        if (ciudad.selectedItemPosition == 0) {
            alert.text = "Por favor, seleccione una ciudad"
            validationResult = false
        }
        if (tipoAdjunto.text.toString().isNullOrEmpty()){
            alert.text="Por favor, ingrese tipo de adjunto"
            validationResult = false
        }
        if (adjunto.isNullOrEmpty() || adjunto.length < 2){
            alert.text="No hay archivo adjunto"
            validationResult = false
        }
        if (validationResult){
            alert.text = ""
        }

        return validationResult
    }

    fun choosePicture(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        println("Antes de start Activity")
        startActivityForResult(intent,R.string.code_select_photo)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("onActivityResult")
        if(resultCode == Activity.RESULT_OK){

            when (requestCode) {
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
                    imageBase64 = image64
                }
            }
        }
        else {
            Toast.makeText(requireContext(), R.string.msg_error_choosing_photo_spn, Toast.LENGTH_SHORT).show()
        }
    }

}
