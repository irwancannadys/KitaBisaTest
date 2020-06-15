package com.example.kitabisatest.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kitabisatest.R
import com.example.kitabisatest.model.BottomSheetValue
import com.example.kitabisatest.model.MovieFavorite
import com.example.kitabisatest.model.ResultMovie
import com.example.kitabisatest.view.detailmovie.MovieDetail
import com.example.kitabisatest.view.favorite.MainFavorite
import com.example.kitabisatest.util.NetworkUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheett.view.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: HomeViewModel
    private var movieType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieType = "popular"

        if (!NetworkUtil.hasNetwork(this)){
            setUpErrorView()
        } else {
            instantiateViewModel()
        }

        btn_fliter.setOnClickListener {
            showBottomSheet()
        }

    }

    private fun instantiateViewModel() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        movieType?.let { viewModel.fetchMovie(it) }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.movie.observe(this, Observer { movie ->
            movie.let { moviesList ->
                rv_list_home.apply {
                    layoutManager = LinearLayoutManager(context)
                    val movieAdapter = HomeAdapter(moviesList.results, object : HomeAdapter.ItemClickListener{
                        override fun onClickMovie(resultMovie: ResultMovie) {
                            MovieDetail.moveToDetailMovie(context, resultMovie)
                        }
                    })
                    adapter = movieAdapter
                }
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            if (isLoading) {
                rv_list_home.visibility = View.GONE
                btn_fliter.visibility = View.GONE
                tv_error.visibility = View.GONE
                tv_retry.visibility = View.GONE
                progress_loading.visibility = View.VISIBLE
            } else {
                rv_list_home.visibility = View.VISIBLE
                btn_fliter.visibility = View.VISIBLE
                tv_error.visibility = View.GONE
                progress_loading.visibility = View.GONE
                tv_retry.visibility = View.GONE
            }
        })

        viewModel.movieLoadError.observe(this, Observer { isError ->
            setUpErrorView()
        })

    }

    private fun setUpErrorView() {
        rv_list_home.visibility = View.GONE
        btn_fliter.visibility = View.GONE
        progress_loading.visibility = View.GONE
        tv_error.visibility = View.VISIBLE
        tv_retry.visibility = View.VISIBLE

        tv_retry.setOnClickListener {
            instantiateViewModel()
        }

    }

    private fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheett, null)
        view.rv_list_bottom_sheet.setHasFixedSize(true)
        view.rv_list_bottom_sheet.layoutManager = LinearLayoutManager(this)

        val listFilter = mutableListOf<BottomSheetValue>()
        listFilter.add(BottomSheetValue(name = "Populer", movieType = "popular"))
        listFilter.add(BottomSheetValue(name = "Upcoming", movieType = "upcoming"))
        listFilter.add(BottomSheetValue(name = "Top Rated", movieType = "top_rated"))
        listFilter.add(BottomSheetValue(name = "Now playing", movieType = "now_playing"))

        val bottomAdapter = BottomSheetAdapter(listFilter, object : BottomSheetAdapter.CategoryListener{
            override fun clickCategory(url: String) {
                viewModel.fetchMovie(url)
                bottomSheetDialog.hide()
            }

        })
        view.rv_list_bottom_sheet.adapter = bottomAdapter

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                MainFavorite.moveToFavorite(applicationContext)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
