package com.example.dudnikov

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dudnikov.data.model.Description
import com.example.dudnikov.data.model.Film
import com.example.dudnikov.data.model.FilmFav
import com.example.dudnikov.data.network.RetroInterface
import com.example.dudnikov.domain.Repository
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PopularAdapter() : RecyclerView.Adapter<CustomViewHolder>() {
    private var listData: List<Film>? = null
    private var checkFav: List<String>? = null
    var myRecDataFav: MutableLiveData<Film> = MutableLiveData()
    var myRecData: MutableLiveData<Film> = MutableLiveData()
    var starId: MutableLiveData<String> = MutableLiveData()
    fun setCheckFav(str: List<String>?) {
        checkFav = str
    }

    fun setListData(listData: List<Film>?) {
        this.listData = listData
    }

    override fun getItemCount(): Int {
        if (listData == null) return 0
        return listData?.size!!
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_row, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val cur = listData?.get(position)!!
        holder.bind(cur, checkFav)
        holder.itemView.setOnClickListener {
            myRecData.value = cur
        }
        holder.itemView.setOnLongClickListener {
            if (holder.star.visibility == View.GONE)
                myRecDataFav.value = cur
            else {
                starId.value = cur.filmId
                holder.star.visibility = View.GONE
            }
            true
        }
    }
}

class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imgView = view.findViewById<ImageView>(R.id.imageView)
    val nameTxt = view.findViewById<TextView>(R.id.nameTxt)
    val genreTxt = view.findViewById<TextView>(R.id.genreTxt)
    val star = view.findViewById<ImageView>(R.id.star)
    fun bind(film: Film, check: List<String>?) {
        nameTxt.setEllipsize(TextUtils.TruncateAt.END)
        nameTxt.text = film.nameRu
        if (film.nameRu.length > 20) nameTxt.text =
            film.nameRu.substring(0, 20) + "..."//ellipsize не работает
        else nameTxt.text = film.nameRu
        genreTxt.text = film.genres[0].genre + "(${film.year})"
        Picasso.get().load(film.posterUrlPreview)
            .into(imgView)
        if (check != null) {
            if (check.contains(film.filmId)) star.visibility = View.VISIBLE
        }
    }
}
