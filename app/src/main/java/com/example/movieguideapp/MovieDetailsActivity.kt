package com.example.movieguideapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

const val MOVIE_POSTER = "movie_poster"
const val MOVIE_TITLE = "movie_title"
const val MOVIE_RATING = "movie_rating"
const val MOVIE_RELEASE_DATE = "movie_release_date"
const val MOVIE_OVERVIEW = "movie_overview"

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var poster: ImageView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        poster = findViewById(R.id.movie_poster)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
    }

    private fun populateDetails(extras: Bundle) {
        title = extras.getString(MOVIE_TITLE)
        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }


        rating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        releaseDate.text = "Release date: ${extras.getString(MOVIE_RELEASE_DATE, "")}"
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
    }
}