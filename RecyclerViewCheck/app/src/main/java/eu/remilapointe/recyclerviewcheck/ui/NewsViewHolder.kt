package eu.remilapointe.recyclerviewcheck.ui

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import eu.remilapointe.recyclerviewcheck.R
import kotlinx.android.synthetic.main.row_news.view.*

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var txtTitle : TextView = itemView.txt_title
    private var txtSourceName: TextView = itemView.txt_source_name
    private var txtDate: TextView = itemView.txt_date
    private var imgThumbnail: ImageView = itemView.img_thumbnail

    fun setTitle(title: String) {
        txtTitle.text = title
    }

    fun setSourceName(sourceName: String) {
        txtSourceName.text = sourceName
    }

    fun setDate(date: String) {
        txtDate.text = date
    }

    fun setThumbnail(context: Context, url: String) {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .apply(RequestOptions().override(100, 100))
            .into(this.imgThumbnail)
    }

}
