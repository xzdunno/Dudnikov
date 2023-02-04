package com.example.dudnikov.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dudnikov.data.model.Description
import com.example.dudnikov.data.model.Top100Data
import com.example.dudnikov.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularItemViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    var myResponse: MutableLiveData<Description> = MutableLiveData()
    fun getDescription(id:String){
        viewModelScope.launch{myResponse.value=repository.getDescription(id)}
    }
}