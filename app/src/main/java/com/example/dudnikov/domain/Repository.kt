package com.example.dudnikov.domain

import com.example.dudnikov.data.model.Description
import com.example.dudnikov.data.model.Top100Data
import com.example.dudnikov.data.network.RetroInterface
import javax.inject.Inject

class Repository @Inject constructor(
    private val retroInterface: RetroInterface
) {
suspend fun getTopData(opt:MutableMap<String,String>): Top100Data =retroInterface.getTop100(opt)
suspend fun getDescription(id:String):Description=retroInterface.getDescription(id)
}