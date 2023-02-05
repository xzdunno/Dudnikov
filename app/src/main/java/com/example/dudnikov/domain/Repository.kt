package com.example.dudnikov.domain

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.dudnikov.data.model.Description
import com.example.dudnikov.data.model.FilmFav
import com.example.dudnikov.data.model.Top100Data
import com.example.dudnikov.data.network.RetroInterface
import com.example.dudnikov.presentation.Constants
import com.example.weather.db.FavDBDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val retroInterface: RetroInterface,
private val favDBDao: FavDBDao
) {
    suspend fun getTopData(opt: MutableMap<String, String>): Top100Data {
        val resp = retroInterface.getTop100(opt)
        if (resp.isSuccessful){
            return resp.body()!!
        }
        else return Top100Data(-1, listOf())
    }

    suspend fun getDescription(id: String): Description {
        val resp = retroInterface.getDescription(id)
        return if (resp.isSuccessful) {
            resp.body()!!
        } else Description(Constants.FAILURE)
    }
    fun insertRecord(fav: FilmFav){
favDBDao.insertRecord(fav)
    }
   /* fun getRecord(id:String):LiveData<FilmFav>{
        return favDBDao.getRecord(id)
    }*/
    fun getAllData():LiveData<List<FilmFav>>{
        return favDBDao.getAllData()
    }
    fun deleteRecord(id:String){
        favDBDao.deleteRecord(id)
    }
}