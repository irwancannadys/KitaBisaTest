package com.example.kitabisatest.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
     @SerializedName("total_results") val totalResult: Int,
     @SerializedName("") val totalPage: Int,
     @SerializedName("results") val results: List<ResultMovie>
)

data class ResultMovie(
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("overview") val overView: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("id") val id: String
) : Serializable