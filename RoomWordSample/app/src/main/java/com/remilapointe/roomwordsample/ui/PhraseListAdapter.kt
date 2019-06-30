package com.remilapointe.roomwordsample.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.remilapointe.roomwordsample.R
import com.remilapointe.roomwordsample.db.Phrase
import com.remilapointe.roomwordsample.db.StringToListConverter

class PhraseListAdapter internal constructor(
    val context: Context,
    private val clickListener: (Phrase) -> View.OnClickListener
) : RecyclerView.Adapter<PhraseListAdapter.PhraseViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var phrases = mutableListOf<Phrase>()  // Cached copy of phrases

    inner class PhraseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val phraseItemView: TextView = itemView.findViewById(R.id.tvPhrase)
        fun bind(phrase: Phrase, listener: (Phrase) -> View.OnClickListener) = phraseItemView.setOnClickListener( listener(phrase) )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhraseViewHolder {
        val itemView = inflater.inflate(R.layout.phrase_recyclerview_item, parent, false)
        return PhraseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhraseViewHolder, position: Int) {
        val current = phrases[position]
        holder.phraseItemView.text = StringToListConverter.wordsListToString(current.mots)
        holder.bind(current, clickListener)
    }

    internal fun setPhrases(phrases: MutableList<Phrase>) {
        this.phrases = phrases
        notifyDataSetChanged()
    }

    fun removePhrase(position: Int) {
        phrases.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = phrases.size

}
