package com.example.weather.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dudnikov.data.model.FilmFav

@Dao
interface FavDBDao {

    @Query("select * from DBFav order by filmId asc")
    fun getAllData(): LiveData<List<FilmFav>>
   /* @Query("select 1 from DBFav where filmId=:id")
    fun getRecord(id:String):LiveData<FilmFav>*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(hourly: FilmFav)

    @Query("delete from DBFav")
    fun deleteAll()
    @Query("delete from DBFav where filmId=:id")
    fun deleteRecord(id:String)

}