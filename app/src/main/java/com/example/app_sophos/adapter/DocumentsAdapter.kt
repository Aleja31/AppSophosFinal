package com.example.app_sophos.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.app_sophos.R
import com.example.app_sophos.model.DataDocuments
import com.example.app_sophos.model.DataListDocument

class DocumentsAdapter (private val dataDocuments: DataDocuments, private val onClickListener:(DataListDocument) -> Unit) : RecyclerView.Adapter<DocumentsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DocumentsViewHolder(layoutInflater.inflate(R.layout.item_documents, parent,false))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: DocumentsViewHolder, position: Int) {
        val item = dataDocuments.Items?.get(position)
        holder.render(item!!, onClickListener)
    }

    override fun getItemCount(): Int {
        return dataDocuments.Count
    }
}