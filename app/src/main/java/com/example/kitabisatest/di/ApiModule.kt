package com.example.kitabisatest.di

import com.example.kitabisatest.model.MovieServiceApi
import com.example.kitabisatest.model.MovieService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "https://api.themoviedb.org/"

    @Provides
    fun provideMovie(): MovieServiceApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MovieServiceApi::class.java)
    }

    @Provides
    fun provideMovieService(): MovieService{
        return MovieService()
    }
}