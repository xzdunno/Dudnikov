package com.example.dudnikov.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
 data class Film(
    val nameRu:String,
    val posterUrl:String,
    val posterUrlPreview:String,
    val genres:List<Genre>,
    val year:Int
):Parcelable
