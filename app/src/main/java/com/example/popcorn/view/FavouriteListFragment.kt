package com.example.popcorn.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.viewmodel.FavouriteViewModel
import com.example.popcorn.viewmodel.PersonViewModel
import com.example.popcorn.viewmodel.adapters.ActorListAdapter
import com.example.popcorn.viewmodel.adapters.FavouriteListAdapter
import kotlinx.android.synthetic.main.fragment_actor_list.*
import kotlinx.android.synthetic.main.fragment_favourite_list.*

class FavouriteListFragment : Fragment() {
    private lateinit var favListAdapter: FavouriteListAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var favVM: FavouriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        favVM = ViewModelProvider(requireActivity()).get(FavouriteViewModel::class.java)

        /*favVM.addFavourite(550)
        favVM.favourites.observe(viewLifecycleOwner, {
            println(favVM.favourites.value.toString())
            if (!favVM.favourites.value.isNullOrEmpty()) favVM.deleteFavorite(550)
        })*/
        myLayoutManager= LinearLayoutManager(context)

        favListAdapter= FavouriteListAdapter(favVM.favourites, favVM)

        favVM.favourites.observe(viewLifecycleOwner, Observer { t ->
            favListAdapter.notifyDataSetChanged()
        })
        return inflater.inflate(R.layout.fragment_favourite_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=rv_favMovieList.apply {
            this.layoutManager=myLayoutManager
            this.adapter=favListAdapter
        }
    }
    companion object { fun newInstance() = FavouriteListFragment() }
}