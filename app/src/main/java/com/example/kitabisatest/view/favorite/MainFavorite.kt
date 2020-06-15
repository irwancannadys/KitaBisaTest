package com.example.kitabisatest.view.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kitabisatest.R
import com.example.kitabisatest.model.MovieFavorite
import com.example.kitabisatest.util.SqliteHelper
import kotlinx.android.synthetic.main.activity_favorite.*

class MainFavorite : AppCompatActivity() {

    var dbHandler: SqliteHelper? = null
    lateinit var listFavorite: MutableList<MovieFavorite>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        initView()
    }

    private fun initView() {
        dbHandler = SqliteHelper(this)
        listFavorite = (dbHandler as SqliteHelper).movie()
        rv_list_favorite.apply {
            layoutManager = LinearLayoutManager(context)
            val favAdapter = FavoriteAdapter(listFavorite, object : FavoriteAdapter.DeleteFavorite{
                override fun delete(id: Int) {
                    val success = dbHandler?.deleteMovie(id) as Boolean
                    if (success) {
                        Toast.makeText(applicationContext, "Success delete from Favorite", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

            })
            adapter = favAdapter
        }
    }


    companion object {
        fun moveToFavorite(context: Context){
            val intent = Intent(context, MainFavorite::class.java)
            context.startActivity(intent)
        }
    }
}