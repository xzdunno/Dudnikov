package com.example.dudnikov.data.model

import com.example.dudnikov.data.model.Film

data class Top100Data(
    val pagesCount:Int,
    val films:List<Film>
)