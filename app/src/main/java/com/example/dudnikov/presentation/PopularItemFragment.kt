package com.example.dudnikov.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.dudnikov.R
import com.example.dudnikov.data.model.Film
import com.squareup.picasso.Picasso

class PopularItemFragment : Fragment() {

    companion object {
        fun newInstance() = PopularItemFragment()
    }

    private lateinit var viewModel: PopularItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PopularItemViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val film=arguments?.getParcelable<Film>("film")
        Log.d("kok",film!!.nameRu)
        val imgView=view?.findViewById<ImageView>(R.id.popItemImg)
        Picasso.get().load(film.posterUrlPreview)
            .into(imgView)
    }
}