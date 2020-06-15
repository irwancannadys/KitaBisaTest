package com.example.kitabisatest.model

import com.example.kitabisatest.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class MovieService {

    @Inject
    lateinit var movieApi: MovieServiceApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getMovie(movieType: String): Single<Movie> {
        return movieApi.getMovieType(movieType)
    }
}