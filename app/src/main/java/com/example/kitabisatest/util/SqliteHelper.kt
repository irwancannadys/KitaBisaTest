package com.example.kitabisatest.util

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.kitabisatest.model.MovieFavorite

class SqliteHelper(context: Context) : SQLiteOpenHelper(context, SqliteHelper.DB_NAME, null, SqliteHelper.DB_VERSION) {


    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "movieDb"
        private val TABLE_NAME = "movie_favorite"
        private val ID = "id"
        private val KEY_TITLE = "title"
        private val KEY_RELEASE_DATE = "releaseDate"
        private val KEY_OVERVIEW = "overView"
        private val KEY_POSTER_PATH = "posterPath"
    }


    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                ID + " INTEGER PRIMARY KEY," +
                KEY_TITLE + " TEXT," + KEY_RELEASE_DATE + " TEXT," +
                KEY_OVERVIEW + " TEXT," +
                KEY_POSTER_PATH + " TEXT);"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addMovie(movieFavorite: MovieFavorite) : Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, movieFavorite.title)
        values.put(KEY_RELEASE_DATE, movieFavorite.releaseDate)
        values.put(KEY_OVERVIEW, movieFavorite.overView)
        values.put(KEY_POSTER_PATH, movieFavorite.posterPath)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    fun getMovie(_id: Int) : MovieFavorite {
        val movie = MovieFavorite()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.moveToNext()) {
                movie.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                movie.title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                movie.releaseDate = cursor.getString(cursor.getColumnIndex(KEY_RELEASE_DATE))
                movie.overView = cursor.getString(cursor.getColumnIndex(KEY_OVERVIEW))
                movie.posterPath = cursor.getString(cursor.getColumnIndex(KEY_POSTER_PATH))
            }
        }
        cursor.close()
        return movie
    }

    fun movie(): MutableList<MovieFavorite>{
            val movieFavorite = mutableListOf<MovieFavorite>()
            val db = writableDatabase
            val selectQuery = "SELECT  * FROM $TABLE_NAME"
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor != null) {
                if (cursor.moveToFirst()){
                    do {
                        val movie = MovieFavorite()
                        movie.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                        movie.title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                        movie.releaseDate = cursor.getString(cursor.getColumnIndex(KEY_RELEASE_DATE))
                        movie.overView = cursor.getString(cursor.getColumnIndex(KEY_OVERVIEW))
                        movie.posterPath = cursor.getString(cursor.getColumnIndex(KEY_POSTER_PATH))
                        movieFavorite.add(movie)
                    } while (cursor.moveToNext())
                }
            }
            cursor.close()
            return movieFavorite
        }

    fun deleteMovie(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
}