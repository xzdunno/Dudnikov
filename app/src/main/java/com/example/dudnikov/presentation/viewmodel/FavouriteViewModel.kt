package com.example.dudnikov.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dudnikov.data.model.FilmFav
import com.example.dudnikov.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class FavouriteViewModel @Inject constructor(private val repository: Repository):ViewModel()
{
    fun getAllData():LiveData<List<FilmFav>> {
        return repository.getAllData()
    }
    fun deleteRecord(id:String){
        repository.deleteRecord(id)
    }
}