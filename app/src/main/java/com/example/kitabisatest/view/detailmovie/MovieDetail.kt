package com.example.kitabisatest.view.detailmovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kitabisatest.R
import com.example.kitabisatest.model.MovieFavorite
import com.example.kitabisatest.model.ResultMovie
import com.example.kitabisatest.util.SqliteHelper
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.Serializable


class MovieDetail : AppCompatActivity() {

    lateinit var resultMovie: ResultMovie
    private var isPressed = true

    var dbHandler: SqliteHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        dbHandler = SqliteHelper(this)
        resultMovie = intent.getSerializableExtra(FLAG_DETAILS_DATA) as ResultMovie

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setHomeButtonEnabled(true);
        getSupportActionBar()?.setTitle(resultMovie.title)



        setDataMovie()
        setClickedImageFavorite()
    }

    private fun setDataMovie() {
        val movieImage = "https://image.tmdb.org/t/p/w500${resultMovie.posterPath}"
        Glide.with(applicationContext)
            .load(movieImage)
            .apply(RequestOptions().override(300, 600))
            .into(iv_image_detail)

        tv_title_detail.text = resultMovie.title
        tv_release_date_detail.text = resultMovie.releaseDate
        tv_description_detail.text = resultMovie.overView
    }

    private fun setClickedImageFavorite() {
        iv_favorite.setOnClickListener {
            if (isPressed){
                iv_favorite.setBackgroundResource(R.drawable.favorite_clicked)
                isPressed = false
                handleAddMovieToFavorite(resultMovie)
                Toast.makeText(applicationContext, "Adding to Favorite", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                iv_favorite.setBackgroundResource(R.drawable.favorite_default)
                isPressed = true
            }
        }
    }

    private fun handleAddMovieToFavorite(resultMovie: ResultMovie) {
        val forFavoriteValue = MovieFavorite()
        forFavoriteValue.title = resultMovie.title
        forFavoriteValue.releaseDate = resultMovie.releaseDate
        forFavoriteValue.overView = resultMovie.overView
        forFavoriteValue.posterPath = resultMovie.posterPath
        dbHandler?.addMovie(forFavoriteValue)
    }

    companion object {
        const val FLAG_DETAILS_DATA = "details_data"
        const val FLAG_LIST_FAVORITE = "list_favorite"
        fun moveToDetailMovie(activity: Context, resultMovie: ResultMovie) {
            val intent = Intent(activity, MovieDetail::class.java)
            intent.putExtra(FLAG_DETAILS_DATA, resultMovie as Serializable)
            activity.startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}