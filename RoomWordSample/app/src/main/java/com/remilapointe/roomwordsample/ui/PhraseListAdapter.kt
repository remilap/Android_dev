package com.remilapointe.roomwordsample.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.remilapointe.roomwordsample.R
import com.remilapointe.roomwordsample.db.Phrase

class PhraseListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<PhraseListAdapter.PhraseViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var phrases = emptyList<Phrase>()  // Cached copy of phrases

    inner class PhraseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val phraseItemView: TextView = itemView.findViewById(R.id.tvPhrase)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhraseViewHolder {
        val itemView = inflater.inflate(R.layout.phrase_recyclerview_item, parent, false)
        return PhraseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhraseViewHolder, position: Int) {
        val current = phrases[position]
        holder.phraseItemView.text = current.mots.joinToString(" ")
    }

    internal fun setPhrases(phrases: List<Phrase>) {
        this.phrases = phrases
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = phrases.size

}
