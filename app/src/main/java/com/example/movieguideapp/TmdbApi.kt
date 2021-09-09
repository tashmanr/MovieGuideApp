package com.example.movieguideapp


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("discover/movie")
    fun discoverMovies(
        @Query("api_key") apiKey: String = "cacdde94532520523cedc277d0579223",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}