package com.example.dudnikov.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.dudnikov.R
import com.example.dudnikov.data.model.Description
import com.example.dudnikov.data.model.Film
import com.example.dudnikov.data.model.Top100Data
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_popular_item.view.*
import kotlinx.android.synthetic.main.popular_row.view.*

@AndroidEntryPoint
class PopularItemFragment : Fragment() {

    companion object {
        fun newInstance() = PopularItemFragment()
    }
    private val viewModelItem: PopularItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val film=arguments?.getParcelable<Film>("film")
        viewModelItem.getDescription(film!!.filmId)
        viewModelItem.myResponse?.observe(requireActivity(), Observer<Description> { response->
            Picasso.get().load(film.posterUrl)
                .into(view.popItemImg)
            view.itemNameTxt.text=film.nameRu
            view.itemDescrTxt.text=response!!.description
            var str="Жанры: "
            for (item in film.genres)
                str+=item.genre+", "
            view.itemGenreTxt.text=str.substring(0,str.length-2)
            str="Страны: "
            for (item in film.countries)
                str+=item.country+", "
            view.itemCountryTxt.text=str.substring(0,str.length-2)
        }
        )
    }
}