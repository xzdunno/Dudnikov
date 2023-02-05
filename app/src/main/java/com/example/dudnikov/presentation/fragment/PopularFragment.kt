package com.example.dudnikov.presentation.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dudnikov.PopularAdapter
import com.example.dudnikov.R
import com.example.dudnikov.data.model.Film
import com.example.dudnikov.data.model.FilmFav
import com.example.dudnikov.data.model.Top100Data
import com.example.dudnikov.databinding.FragmentPopularBinding
import com.example.dudnikov.presentation.viewmodel.PopularViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class PopularFragment() : Fragment() {
    lateinit var recViewPopAdapter: PopularAdapter
    private var _binding: FragmentPopularBinding? = null
    private val bind get() = _binding!!

    companion object {
        fun newInstance() = PopularFragment()
    }

    private val viewModel: PopularViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return bind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recView = view.findViewById<RecyclerView>(R.id.recView)
        recView.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
            recViewPopAdapter = PopularAdapter()
            adapter = recViewPopAdapter
        }
        recViewPopAdapter.myRecDataFav.observe(requireActivity()) {
            viewModel.insertRecord(
                FilmFav(
                    it.filmId,
                    it.nameRu,
                    it.posterUrl,
                    it.posterUrlPreview,
                    it.genres[0].genre,
                    it.year,
                    it.countries[0].country,
                    null
                )
            )
        }
        bind.toFavBtn.setOnClickListener {
            findNavController(this).navigate(R.id.favouriteFragment)
        }
        recViewPopAdapter.starId.observe(requireActivity(),){
            viewModel.deleteRecord(it)
        }
        recViewPopAdapter.myRecData.observe(requireActivity())
        {
            val cur = it
            val bundle = bundleOf(
                "film" to Film(
                    cur.filmId,
                    cur.nameRu,
                    cur.posterUrl,
                    cur.posterUrlPreview,
                    cur.genres,
                    cur.year,
                    cur.countries
                )
            )
            findNavController(this).navigate(R.id.popularItemFragment, bundle)
        }
        val map = mutableMapOf<String, String>()
        map.put("type", "TOP_100_POPULAR_FILMS")
        map.put("page", "1")


        fun setting() {
            lifecycleScope.launchWhenCreated {
                viewModel.isConnected.collectLatest {
                    if (it) {
                        viewModel.getTopData(map)
                        bind.noConLinear.visibility = View.GONE
                        bind.recView.visibility = View.VISIBLE
                    } else {
                        bind.noConLinear.visibility = View.VISIBLE
                        bind.recView.visibility = View.GONE

                    }
                }
            }
        }
        setting()
        bind.repeatBtn.setOnClickListener {
            setting()
        }
        viewModel.myResponse.observe(requireActivity(), Observer<Top100Data> { response ->
            if (response.pagesCount != -1) {
                viewModel.getAllData().observe(requireActivity(), Observer<List<FilmFav>> {
                    recViewPopAdapter.setListData(response!!.films)
                    if (it != null) {
                        val list = mutableListOf<String>()
                        for (item in it) list.add(item.filmId)
                        recViewPopAdapter.setCheckFav(list)
                    }
                    recViewPopAdapter.notifyDataSetChanged()
                })

            } else {
            }
        }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}