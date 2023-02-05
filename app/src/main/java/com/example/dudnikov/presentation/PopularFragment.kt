package com.example.dudnikov.presentation


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dudnikov.PopularAdapter
import com.example.dudnikov.R
import com.example.dudnikov.data.model.Top100Data
import com.example.dudnikov.databinding.FragmentPopularBinding
import com.example.dudnikov.databinding.FragmentPopularItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class PopularFragment() : Fragment() {
    lateinit var recViewPopAdapter: PopularAdapter
    private var _binding: FragmentPopularBinding?=null
    private val bind get() = _binding!!
    companion object {
        fun newInstance() = PopularFragment()
    }

    private val viewModel: PopularViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentPopularBinding.inflate(inflater,container,false)
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

        val map = mutableMapOf<String, String>()
        map.put("type", "TOP_100_POPULAR_FILMS")
        map.put("page", "1")


        fun setting(){
            lifecycleScope.launchWhenCreated {
            viewModel.isConnected.collectLatest {
                if(it){
                    viewModel.getTopData(map)
                    bind.noConLinear.visibility=View.GONE
                    bind.recView.visibility=View.VISIBLE
                }else{
                    bind.noConLinear.visibility=View.VISIBLE
                    bind.recView.visibility=View.GONE

                }
            }
        }
        }
        setting()
        bind.repeatBtn.setOnClickListener{
            setting()
        }
        viewModel.myResponse.observe(requireActivity(), Observer<Top100Data> { response ->
            if (response.pagesCount != -1) {
                recViewPopAdapter.setListData(response!!.films)
                recViewPopAdapter.notifyDataSetChanged()
            } else {
            }
        }
        )
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}