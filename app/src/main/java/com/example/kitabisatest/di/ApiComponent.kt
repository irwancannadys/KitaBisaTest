package com.example.kitabisatest.di

import com.example.kitabisatest.model.MovieService
import com.example.kitabisatest.view.HomeViewModel
import dagger.Component


@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: MovieService)
    fun inject(homeViewModel: HomeViewModel)
}