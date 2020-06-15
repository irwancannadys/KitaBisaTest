package com.example.kitabisatest.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kitabisatest.model.Movie
import com.example.kitabisatest.model.MovieService
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class HomeViewModelTest {

    @get: Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var movieService: MovieService

    @InjectMocks
    var viewModel = HomeViewModel()

    private var testSingle: Single<Movie>? = null

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getMovieSuccess() {
        val movie = Movie(totalResult = 20, totalPage = 1, results = arrayListOf())

        testSingle = Single.just(movie)

        Mockito.`when`(movieService.getMovie("movieType")).thenReturn(testSingle)

        viewModel.fetchMovie("movieType")
        viewModel.movieLoadError.value = false

        Assert.assertEquals(movie, viewModel.movie.value)

        Assert.assertEquals(false, viewModel.movieLoadError.value)

        Assert.assertEquals(false, viewModel.loading.value)

    }

    @Test
    fun getMovieFail(){

        testSingle = Single.error(Throwable())

        `when`(movieService.getMovie("movieType")).thenReturn(testSingle)

        viewModel.fetchMovie("movieType")

        Assert.assertEquals(true, viewModel.movieLoadError.value)

        Assert.assertEquals(false, viewModel.loading.value)

    }

    @Before
    fun setUpRxSchedulers(){
        val immidiate = object: Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler{ scheduler: Callable<Scheduler>? -> immidiate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler: Callable<Scheduler>? -> immidiate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler: Callable<Scheduler>? ->  immidiate}
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler: Callable<Scheduler>? -> immidiate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler>? -> immidiate }

    }
}
