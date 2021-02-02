package com.example.popcorn.view.general

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.viewmodel.FavouriteViewModel
import com.example.popcorn.viewmodel.MovieViewModel
import com.example.popcorn.viewmodel.TVShowViewModel
import com.example.popcorn.viewmodel.adapters.general.FavouriteListAdapter
import kotlinx.android.synthetic.main.fragment_favourite_list.*

class FavouriteListFragment : Fragment() {
    private lateinit var favListAdapter : FavouriteListAdapter
    private lateinit var myLayoutManager : LinearLayoutManager
    private lateinit var recyclerView : RecyclerView
    private lateinit var favVM : FavouriteViewModel
    private lateinit var movieVM : MovieViewModel
    private lateinit var tvsVM : TVShowViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        myLayoutManager = LinearLayoutManager(context)

        movieVM = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        favVM = ViewModelProvider(requireActivity()).get(FavouriteViewModel::class.java)
        tvsVM = ViewModelProvider(requireActivity()).get(TVShowViewModel::class.java)

        favListAdapter = FavouriteListAdapter(favVM.favourites, favVM, movieVM, tvsVM)
        favVM.favourites.observe(viewLifecycleOwner, { favListAdapter.notifyDataSetChanged() })

        return inflater.inflate(R.layout.fragment_favourite_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = rv_favList.apply {
            this.layoutManager = myLayoutManager
            this.adapter = favListAdapter
        }
    }
}