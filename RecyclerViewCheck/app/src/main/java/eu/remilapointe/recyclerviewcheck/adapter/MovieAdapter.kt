package eu.remilapointe.recyclerviewcheck.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.remilapointe.recyclerviewcheck.R
import eu.remilapointe.recyclerviewcheck.model.Movie
import eu.remilapointe.recyclerviewcheck.ui.MovieViewHolder

class MovieAdapter(var context: Context, var movieList: ArrayList<Movie>) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.setMovieName(movie.name)
        holder.setDirectorName(movie.directorName)
        holder.setYear(movie.year)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}