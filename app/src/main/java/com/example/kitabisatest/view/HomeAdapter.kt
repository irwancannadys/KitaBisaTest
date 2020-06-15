package com.example.kitabisatest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kitabisatest.R
import com.example.kitabisatest.model.ResultMovie
import kotlinx.android.synthetic.main.item_view_home.view.*

class HomeAdapter(var listMovie: List<ResultMovie>, var itemClickListener: ItemClickListener) : RecyclerView.Adapter<HomeAdapter.MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
       return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_home, parent, false))
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindView(listMovie[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClickMovie(listMovie[position])
        }
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageMovie = view.iv_image_home
        private val title = view.tv_title
        private val releaseDate = view.tv_release_date
        private val description = view.tv_description

        fun bindView(resultMovie: ResultMovie) {
            title.text = resultMovie.title
            releaseDate.text = resultMovie.releaseDate
            description.text = resultMovie.overView

            val movieImage = "https://image.tmdb.org/t/p/w500${resultMovie.posterPath}"

            Glide.with(itemView).load(movieImage).apply(RequestOptions().override(300, 600).placeholder(R.drawable.ic_launcher_background)).into(imageMovie)
        }
    }

    interface ItemClickListener {
        fun onClickMovie(resultMovie: ResultMovie)
    }


}