package com.example.dudnikov.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.dudnikov.R
import com.example.dudnikov.data.model.Description
import com.example.dudnikov.data.model.Film
import com.example.dudnikov.data.model.Top100Data
import com.example.dudnikov.databinding.FragmentPopularItemBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PopularItemFragment : Fragment() {
    private var _binding: FragmentPopularItemBinding? = null
    private val bind get() = _binding!!

    companion object {
        fun newInstance() = PopularItemFragment()
    }

    private val viewModelItem: PopularItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularItemBinding.inflate(inflater, container, false)
        return bind.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val film = arguments?.getParcelable<Film>("film")!!
        lifecycleScope.launchWhenCreated {
            viewModelItem.isConnected.collectLatest {
                if (it) {
                    viewModelItem.getDescription(film!!.filmId)
                    bind.noConLinearItem.visibility = View.GONE
                } else {
                    bind.noConLinearItem.visibility = View.VISIBLE
                }
            }
        }

        viewModelItem.myResponse?.observe(requireActivity(), Observer<Description> { response ->
            if (response.description != Constants.FAILURE) {

                Picasso.get().load(film.posterUrl)
                    .into(bind.popItemImg)
                bind.itemNameTxt.text = film.nameRu
                bind.itemDescrTxt.text = response!!.description
                var str = "Жанры: "
                for (item in film.genres)
                    str += item.genre + ", "
                bind.itemGenreTxt.text = str.substring(0, str.length - 2)
                str = "Страны: "
                for (item in film.countries)
                    str += item.country + ", "

                bind.itemCountryTxt.text = str.substring(0, str.length - 2)
            } else {

            }
        }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
