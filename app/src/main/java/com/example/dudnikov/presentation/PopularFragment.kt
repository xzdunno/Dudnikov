package com.example.dudnikov.presentation


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dudnikov.PopularAdapter
import com.example.dudnikov.R
import com.example.dudnikov.data.model.Top100Data

class PopularFragment : Fragment() {
    lateinit var recViewPopAdapter: PopularAdapter
    companion object {
        fun newInstance() = PopularFragment()
    }

    private val viewModel: PopularViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel = ViewModelProvider(this).get(PopularViewModel::class.java)
        val recView = view.findViewById<RecyclerView>(R.id.recView)
        recView.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL, false
            )
            recViewPopAdapter = PopularAdapter()
            adapter = recViewPopAdapter
        }
        val map= mutableMapOf<String,String>()
        map.put("type","TOP_100_POPULAR_FILMS")
        map.put("page","1")
        viewModel.getTopData(map)
        viewModel.myResponse?.observe(requireActivity(), Observer<Top100Data> { response->
            Log.d("kok", response!!.pagesCount.toString())
            Log.d("kok",response!!.films.size.toString())
            Log.d("kok",response!!.films.toString())
            recViewPopAdapter.setListData(response!!.films)
            recViewPopAdapter.notifyDataSetChanged()
        }
        )
    }

}