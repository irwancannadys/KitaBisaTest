package com.example.kitabisatest.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kitabisatest.di.DaggerApiComponent
import com.example.kitabisatest.model.Movie
import com.example.kitabisatest.model.MovieService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    @Inject
    lateinit var movieService: MovieService
    private val disposable = CompositeDisposable()

    val movie = MutableLiveData<Movie>()
    val movieLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun fetchMovie(movieType: String) {
        loading.value = true
        disposable.add(
            movieService.getMovie(movieType)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movie>() {
                    override fun onSuccess(value: Movie?) {
                        movie.value = value
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        movieLoadError.value = true
                        loading.value = false
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}