package com.example.dudnikov.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dudnikov.data.model.Top100Data
import com.example.dudnikov.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    var myResponse: MutableLiveData<Top100Data> = MutableLiveData()
    fun getTopData(opt:MutableMap<String,String>){
        viewModelScope.launch{myResponse.value=repository.getTopData(opt)}
    }
}