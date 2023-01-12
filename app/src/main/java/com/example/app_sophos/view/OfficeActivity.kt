package com.example.app_sophos.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.example.app_sophos.R
import com.example.app_sophos.common.PrivilegesRequests
import com.example.app_sophos.databinding.OfficeBinding
import com.example.app_sophos.model.DataOffices
import com.example.app_sophos.viewModel.OfficesViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class OfficeActivity : Fragment(),  OnMapReadyCallback {

    private var _binding: OfficeBinding?=null
    private val officeBinding get() = _binding!!
    private lateinit var map: GoogleMap
    val DataOfficesViewModel: OfficesViewModel by viewModels()
    private lateinit var fusedLocation: FusedLocationProviderClient
    //private var listOffices:List<DataOffices>?= listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OfficeBinding.inflate(inflater, container, false)
        fusedLocation = LocationServices.getFusedLocationProviderClient(requireContext())
        return officeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Observer to check if there is any change on the DataModel
        DataOfficesViewModel.getOfficesViewModelObserver().observe(viewLifecycleOwner){
            if (it.Items.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Hubo un problema en la conexion al servidor", Toast.LENGTH_SHORT).show()
            }
            else{
                createMarker(it.Items)
            }
        }


        // Acomodar los permisos
        val privilegeRequest = PrivilegesRequests(requireActivity() as AppCompatActivity,R.string.code_location_permission,Manifest.permission.ACCESS_FINE_LOCATION)
        val hasPermission = privilegeRequest.startCheckingPermission()
        if (hasPermission){
            createFragment()
            DataOfficesViewModel.getOffices(null)
        }

    }

    private fun createFragment() {
        val mapFragment: SupportMapFragment = FragmentManager.findFragment(view?.findViewById(R.id.map)!!)
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.isMyLocationEnabled = true
        map.uiSettings.isZoomControlsEnabled = true

        fusedLocation.lastLocation.addOnSuccessListener {
            if (it != null){
                val coordinates = LatLng(it.latitude,it.longitude)
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(coordinates,18f),
                    4000,
                    null
                )
            }
        }
    }

    private fun createMarker(locations: List<DataOffices>?) {
        for (i in locations!!){
            val coordinates = LatLng(i.Latitud!!.toDouble(), i.Longitud!!.toDouble())
            val marker: MarkerOptions = MarkerOptions().position(coordinates).title(i.Nombre)
            map.addMarker(marker)
        }
    }
}