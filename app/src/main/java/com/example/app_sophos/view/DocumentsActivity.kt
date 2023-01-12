package com.example.app_sophos.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import com.example.app_sophos.R
import com.example.app_sophos.adapter.DocumentsAdapter
import com.example.app_sophos.common.GeneralActions.Companion.prefer
import com.example.app_sophos.databinding.DocumentsBinding
import com.example.app_sophos.databinding.SendDocumentsBinding
import com.example.app_sophos.model.DataDocuments
import com.example.app_sophos.model.DataListDocument
import com.example.app_sophos.viewModel.DocumentsViewModel

class DocumentsActivity : Fragment() {

    private var _binding: DocumentsBinding? = null
    private val documentsBinding get() = _binding!!
    private val DataDocumentsViewModel : DocumentsViewModel by viewModels()
    private var decodedImages:Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DocumentsBinding.inflate(inflater,container, false)
        DataDocumentsViewModel.getDocuments(prefer.getEmail())

        DataDocumentsViewModel.getDocumentViewModelObserver().observe(viewLifecycleOwner){
            val imageBytes = Base64.decode(it.Items?.get(0)?.Adjunto, Base64.DEFAULT)
            decodedImages = BitmapFactory.decodeByteArray(imageBytes,0, imageBytes.size)

            if (decodedImages == null){
                Toast.makeText(context, "Documento sin imagen", Toast.LENGTH_LONG).show()
            }
            else createViewMode(decodedImages!!)
        }

        DataDocumentsViewModel.getDocumentsViewModelObserver().observe(viewLifecycleOwner) {
            if (it.Count>0){
                initRecyclerView(documentsBinding.root,it)
            }
        }
        return documentsBinding.root
    }


    private fun initRecyclerView(view: View, datadocuments : DataDocuments){
        val manager = LinearLayoutManager(context)
        val decoration = DividerItemDecoration(context, manager.orientation)
       // documentsBinding.
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerDocuments)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = DocumentsAdapter(datadocuments) { onItemSelected(it) }
        recyclerView.addItemDecoration(decoration)

    }

    fun onItemSelected (document: DataListDocument){
        DataDocumentsViewModel.getDocument(document.IdRegistro)
    }

    fun createViewMode(image: Bitmap){
        var dialog = FragmentViewPhoto(image)
        dialog.show(parentFragmentManager,"customDialog")
    }
}
