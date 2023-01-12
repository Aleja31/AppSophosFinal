package com.example.app_sophos.adapter

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.app_sophos.R
import com.example.app_sophos.databinding.ItemDocumentsBinding
import com.example.app_sophos.model.DataDocuments
import com.example.app_sophos.model.DataListDocument
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.logging.SimpleFormatter

class DocumentsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemDocumentsBinding.bind(view)


    @RequiresApi(Build.VERSION_CODES.N)
    fun render (listDataDocuments: DataListDocument, onClickListener:(DataListDocument) -> Unit) {

        binding.textViewDate.text = formatDate(listDataDocuments.Fecha)
        binding.texViewTypeDocument.text = listDataDocuments.TipoAdjunto
        binding.textViewName.text = listDataDocuments.Nombre

        itemView.setOnClickListener{
                onClickListener(listDataDocuments)
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun formatDate(date: String): String{

        var date = date.slice(0..9).replace("-","/")
        var pattern = SimpleDateFormat("yyyy/MM/dd")
        var finalDate = pattern.parse(date)

        pattern = SimpleDateFormat("dd/MM/yy")
        return pattern.format(finalDate)

    }
}
