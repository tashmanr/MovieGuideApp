package com.example.movieguideapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var movies: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesLayoutManager: LinearLayoutManager
    private var moviesPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movies = findViewById(R.id.movies)
        moviesLayoutManager = GridLayoutManager(
            this, 2, LinearLayoutManager.VERTICAL, false
        )
        movies.layoutManager = moviesLayoutManager
        moviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        movies.adapter = moviesAdapter

        discoverMovies()
    }

    private fun discoverMovies() {
        MoviesRepository.discoverMovies(moviesPage,
            onSuccess = { movies ->
                moviesAdapter.updateMovies(movies)
                attachMoviesOnScrollListener()
            },
            onError = {
                Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT)
                    .show()
            })
    }

    private fun attachMoviesOnScrollListener() {
        movies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = moviesLayoutManager.itemCount
                val visibleItemCount = moviesLayoutManager.childCount
                val firstVisibleItem = moviesLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    movies.removeOnScrollListener(this)
                    moviesPage++
                    discoverMovies()
                }
            }
        })
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }

}