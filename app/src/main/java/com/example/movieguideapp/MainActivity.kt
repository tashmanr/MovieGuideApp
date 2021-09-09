package com.example.movieguideapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
        moviesLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        movies.layoutManager = moviesLayoutManager
        moviesAdapter = MoviesAdapter(mutableListOf())
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

}