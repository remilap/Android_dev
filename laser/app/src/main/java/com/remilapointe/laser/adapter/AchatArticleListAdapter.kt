package com.remilapointe.laser.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.remilapointe.laser.ui.viewmodel.LaserViewModel

class AchatArticleListAdapter internal constructor(
    context: Context,
    private val laserViewModel: LaserViewModel
) : RecyclerView.Adapter<AchatArticleListAdapter.AchatArticleViewHolder>() {

    inner class AchatArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchatArticleViewHolder {
        return AchatArticleViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AchatArticleViewHolder, position: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return 5
    }

}
