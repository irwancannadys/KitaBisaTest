package com.example.kitabisatest.view.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kitabisatest.R
import com.example.kitabisatest.model.MovieFavorite
import com.example.kitabisatest.model.ResultMovie
import kotlinx.android.synthetic.main.item_view_home.view.*

class FavoriteAdapter(private val listFavorite: MutableList<MovieFavorite>, val listener: DeleteFavorite): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolderItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolderItem {
        return FavoriteViewHolderItem(LayoutInflater.from(parent.context).inflate(R.layout.item_view_favorite, parent, false))
    }

    override fun getItemCount(): Int = listFavorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolderItem, position: Int) {
        holder.bindView(listFavorite[position])
        holder.itemView.setOnClickListener {
            listener.delete(listFavorite[position].id)
        }
    }

    class FavoriteViewHolderItem(view: View) : RecyclerView.ViewHolder(view) {
        private val imageMovie = view.iv_image_home
        private val title = view.tv_title
        private val releaseDate = view.tv_release_date
        private val description = view.tv_description

        fun bindView(resultMovie: MovieFavorite) {
            title.text = resultMovie.title
            releaseDate.text = resultMovie.releaseDate
            description.text = resultMovie.overView
            val movieImage = "https://image.tmdb.org/t/p/w500${resultMovie.posterPath}"
            Glide.with(itemView).load(movieImage).apply(RequestOptions().override(300, 600)).into(imageMovie)
        }
    }

    interface DeleteFavorite {
        fun delete(id: Int)
    }
}