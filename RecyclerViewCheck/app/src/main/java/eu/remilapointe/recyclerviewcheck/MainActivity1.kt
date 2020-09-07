package eu.remilapointe.recyclerviewcheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.remilapointe.recyclerviewcheck.adapter.MovieAdapter
import eu.remilapointe.recyclerviewcheck.model.Movie

class MainActivity1 : AppCompatActivity() {
    var movieList: ArrayList<Movie> = arrayListOf()
    lateinit var rvMovies: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_1)
        rvMovies = findViewById(R.id.rv_movies)

        generateData()
        setData()
    }

    private fun generateData() {
        movieList.add(Movie("Inception", "2010", "Christopher Nolan"))
        movieList.add(Movie("The Social Network", "2010", "David Fincher"))
        movieList.add(Movie("Mad Max: Fury Road", "2015", "George Miller"))
        movieList.add(Movie("12 Years a Slave", "2013", "Steve McQueen"))
        movieList.add(Movie("Whiplash", "2014", "Damien Chazelle"))
        movieList.add(Movie("Prisoners", "2013", "Denis Villeneuve"))
        movieList.add(Movie("Arrival", "2016", "Denis Villeneuve"))
        movieList.add(Movie("Django Unchained", "2012", "Quentin Tarantino"))
    }

    private fun setData() {
        rvMovies.layoutManager = LinearLayoutManager(this)
        rvMovies.adapter = MovieAdapter(this, movieList)
    }

}
