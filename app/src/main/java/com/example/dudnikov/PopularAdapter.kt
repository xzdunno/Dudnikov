package com.example.dudnikov

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dudnikov.data.model.Film
import com.squareup.picasso.Picasso

class PopularAdapter(/*val all: MainActivity.all*/) : RecyclerView.Adapter<CustomViewHolder>() {
    private var listData: List<Film>? = null
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
        val cur=listData?.get(position)!!
        holder.bind(cur)
        holder.itemView.setOnClickListener {
            val bundle= bundleOf(
                "film" to Film(cur.nameRu,cur.posterUrl,cur.posterUrlPreview,cur.genres,cur.year)
            )
it.findNavController().navigate(R.id.popularItemFragment, bundle)

        }
    }
}

class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
val imgView=view.findViewById<ImageView>(R.id.imageView)
val nameTxt=view.findViewById<TextView>(R.id.nameTxt)
    val genreTxt=view.findViewById<TextView>(R.id.genreTxt)
    fun bind(film: Film) {
        nameTxt.setEllipsize(TextUtils.TruncateAt.END)
nameTxt.text=film.nameRu
        if (film.nameRu.length>20) nameTxt.text=film.nameRu.substring(0,20)+"..."//ellipsize не работает
        else nameTxt.text=film.nameRu
        genreTxt.text= film.genres[0].genre+"(${film.year})"
        Picasso.get().load(film.posterUrlPreview)
            .into(imgView)

    }
}