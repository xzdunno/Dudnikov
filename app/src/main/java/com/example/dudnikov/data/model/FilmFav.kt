package com.example.dudnikov.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "DBFav")
data class FilmFav(
    @PrimaryKey
    val filmId:String,
                   val nameRu:String,
                   val posterUrl:String,
                   val posterUrlPreview:String,
                   val genre:String,
                   val year:Int,
                   val country:String,
                   val description: String?)
