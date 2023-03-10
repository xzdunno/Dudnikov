package com.example.dudnikov.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dudnikov.data.model.FilmFav
import com.example.dudnikov.data.model.Top100Data
import com.example.dudnikov.data.network.ListenNetwork
import com.example.dudnikov.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(private val repository: Repository, private val listenNetwork: ListenNetwork):ViewModel() {
    var myResponse: MutableLiveData<Top100Data> = MutableLiveData()
    val isConnected : Flow<Boolean> = listenNetwork.isConnected
    fun getTopData(opt:MutableMap<String,String>){
        viewModelScope.launch{myResponse.value=repository.getTopData(opt)}
    }
    fun insertRecord(fav: FilmFav){
        repository.insertRecord(fav)
    }
    fun getAllData(): LiveData<List<FilmFav>> {
        return repository.getAllData()
    }
    fun deleteRecord(id:String){
        repository.deleteRecord(id)
    }
}