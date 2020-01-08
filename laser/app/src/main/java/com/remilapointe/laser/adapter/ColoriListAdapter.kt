package com.remilapointe.laser.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.log4k.d
import com.remilapointe.laser.R
import com.remilapointe.laser.db.Colori

class ColoriListAdapter internal constructor(
    context: Context,
    private val editListener: OnSaveItems,
    private val clickListener: (Colori) -> View.OnClickListener
    ) : RecyclerView.Adapter<ColoriListAdapter.ColoriViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var colorisList = mutableListOf<Colori>()

    interface OnSaveItems {
        fun saveColori(position: Int, vararg items: Colori?)
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    fun EditText.onChange(cb: (String) -> Unit) {
        this.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) { cb(s.toString()) }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    inner class ColoriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val strIdItemView: TextView = itemView.findViewById(R.id.tvColoriItem)
//        val stringItemView: EditText = itemView.findViewById(R.id.tvColoriItem)
        val stringItemView: TextView = itemView.findViewById(R.id.tvColoriItem)
        fun bind(colori: Colori, listener: (Colori) -> View.OnClickListener) = stringItemView.setOnClickListener( listener(colori) )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColoriViewHolder {
        val itemView = inflater.inflate(R.layout.colori_recyclerview_item, parent, false)
        return ColoriViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ColoriViewHolder, position: Int) {
        val current = colorisList[position]
//        holder.strIdItemView.text = current.id.toString()
//        holder.stringItemView.text = current.elem.toEditable()
//        holder.stringItemView.onChange { editListener.saveColori(position, colorisList[position]) }
        holder.stringItemView.text = current.elem
        holder.bind(current, clickListener)
    }

    internal fun setColoris(strs: MutableList<Colori>) {
        this.colorisList = strs
        d("setColoris with ${strs.size} elems")
        notifyDataSetChanged()
    }

    fun get(position: Int): Colori {
        val colori = colorisList.get(position)
        d("get Colori position $position value ${colori.elem}")
        return colori
    }

    override fun getItemCount(): Int = colorisList.size

}
