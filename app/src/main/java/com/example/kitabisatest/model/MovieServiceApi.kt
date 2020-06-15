package com.example.kitabisatest.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieServiceApi {

    @GET("3/movie/{movieType}?api_key=43a8d07c6d77aa471ff599db8e7fbe88&language=en-US")
    fun getMovieType(@Path("movieType") path: String): Single<Movie>
}