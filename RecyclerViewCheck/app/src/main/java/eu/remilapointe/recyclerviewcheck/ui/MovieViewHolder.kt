package eu.remilapointe.recyclerviewcheck.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_movie.view.*

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var txtMovieName: TextView = itemView.txt_movie_name
    var txtDirectorName: TextView = itemView.txt_director_name
    var txtYear: TextView = itemView.txt_year

    fun setMovieName(movieName: String) {
        txtMovieName.text = movieName
    }
    fun setDirectorName(directorName: String) {
        txtDirectorName.text = directorName
    }
    fun setYear(year: String) {
        txtYear.text = year
    }
}