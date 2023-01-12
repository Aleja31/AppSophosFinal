package com.example.app_sophos.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.app_sophos.R

import com.example.app_sophos.databinding.MenuScreenBinding

class MenuScreenActivity : Fragment () {

    private var _binding: MenuScreenBinding? = null
    private val menuBinding get() = _binding!!


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        menuBinding = MenuScreenBinding.inflate(layoutInflater)
//        setContentView(menuBinding.root)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = MenuScreenBinding.inflate(inflater, container,false)
        return menuBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val navController =  findNavController()

        menuBinding.buttonDocuments.setOnClickListener {
            navController.navigate(R.id.action_MenuScreenToSendDocuments)
        }

        menuBinding.buttonViewDocuments.setOnClickListener {
            navController.navigate(R.id.action_MenuScreenToViewDocuments)
        }

        menuBinding.buttonLocation.setOnClickListener {
            navController.navigate(R.id.action_MenuScreenToOffice)
        }


    }

}
