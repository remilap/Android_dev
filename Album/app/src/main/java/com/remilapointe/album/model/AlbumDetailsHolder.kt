package com.remilapointe.album.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.remilapointe.album.R
import com.remilapointe.album.entity.Album

class AlbumDetailsHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.album_details, parent, false)) {

    private val nameView = itemView.findViewById<TextView>(R.id.txt_name)
    private val createdView = itemView.findViewById<TextView>(R.id.txt_created)
    private val modifiedView = itemView.findViewById<TextView>(R.id.txt_modified)
    private val pinnedView = itemView.findViewById<CheckBox>(R.id.chk_pinned)
    private val hiddenView = itemView.findViewById<CheckBox>(R.id.chk_hidden)
    private val deletedView = itemView.findViewById<CheckBox>(R.id.chk_deleted)

    var album : Album? = null


}