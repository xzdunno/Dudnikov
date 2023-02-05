package com.example.dudnikov

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.dudnikov.data.model.Film
import com.example.dudnikov.data.model.FilmFav
import com.squareup.picasso.Picasso

class FavouriteAdapter() : RecyclerView.Adapter<CustomViewHolderFav>() {
    private var listData: List<FilmFav>?=null
    var starId: MutableLiveData<String> = MutableLiveData()
    fun setListData(listData: List<FilmFav>?) {
        this.listData = listData
    }

    override fun getItemCount(): Int {
        if (listData == null) return 0
        return listData?.size!!
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolderFav {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_row, parent, false)
        return CustomViewHolderFav(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolderFav, position: Int) {
        val cur=listData?.get(position)!!
        holder.bind(cur)
        holder.itemView.setOnLongClickListener {
                starId.value = cur.filmId
            true
        }
    }
}

class CustomViewHolderFav(view: View) : RecyclerView.ViewHolder(view) {
    val imgView=view.findViewById<ImageView>(R.id.imageView)
    val nameTxt=view.findViewById<TextView>(R.id.nameTxt)
    val genreTxt=view.findViewById<TextView>(R.id.genreTxt)
    val star=view.findViewById<ImageView>(R.id.star)
    fun bind(film: FilmFav) {
        nameTxt.setEllipsize(TextUtils.TruncateAt.END)
        nameTxt.text=film.nameRu
        if (film.nameRu.length>20) nameTxt.text=film.nameRu.substring(0,20)+"..."//ellipsize не работает
        else nameTxt.text=film.nameRu
        genreTxt.text= film.genre+"(${film.year})"
        Picasso.get().load(film.posterUrlPreview)
            .into(imgView)
star.visibility=View.VISIBLE
    }
}