package com.example.dudnikov.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dudnikov.FavouriteAdapter
import com.example.dudnikov.R
import com.example.dudnikov.data.model.FilmFav
import com.example.dudnikov.presentation.viewmodel.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {
    lateinit var recViewFavAdapter: FavouriteAdapter
    companion object {
        fun newInstance() = FavouriteFragment()
    }

    private val viewModelFav: FavouriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recView = view.findViewById<RecyclerView>(R.id.recViewFav)
        recView.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
            recViewFavAdapter = FavouriteAdapter()
            adapter = recViewFavAdapter
        }
viewModelFav.getAllData().observe(requireActivity(), Observer<List<FilmFav>>{
    recViewFavAdapter.setListData(it)
    recViewFavAdapter.notifyDataSetChanged()
})
val btn=view.findViewById<Button>(R.id.toPopularBtn)
        btn.setOnClickListener{
            findNavController(this).navigate(R.id.popularFragment)
        }
        recViewFavAdapter.starId.observe(requireActivity(),){
            viewModelFav.deleteRecord(it)
        }
    }
}