package com.example.popcorn.view.general

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.viewmodel.FavouriteViewModel
import com.example.popcorn.viewmodel.MovieViewModel
import com.example.popcorn.viewmodel.TVShowViewModel
import com.example.popcorn.viewmodel.adapters.general.FavouriteListAdapter
import kotlinx.android.synthetic.main.fragment_general_list.*

// Fragment displayed in fifth tab:
class FavouriteListFragment : Fragment() {
    // ViewModels:
    private lateinit var favViewModel : FavouriteViewModel
    private lateinit var movieViewModel : MovieViewModel
    private lateinit var tvShowViewModel : TVShowViewModel

    // Adapter and its RecyclerView:
    private lateinit var favListAdapter : FavouriteListAdapter
    private lateinit var myLayoutManager : LinearLayoutManager
    private lateinit var recyclerView : RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        // ViewModels:
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        favViewModel = ViewModelProvider(requireActivity()).get(FavouriteViewModel::class.java)
        tvShowViewModel = ViewModelProvider(requireActivity()).get(TVShowViewModel::class.java)

        // LinearLayoutManager (used by RecyclerView):
        myLayoutManager = LinearLayoutManager(context)

        // Adapter (used by RecyclerView):
        favListAdapter = FavouriteListAdapter(favViewModel.favouritesWithMatchingTitle, favViewModel, movieViewModel, tvShowViewModel)

        // Updating RecyclerView after receiving new data from local database:
        favViewModel.favourites.observe(viewLifecycleOwner, { favListAdapter.notifyDataSetChanged(); favViewModel.setFavouritesWithMatchingTitle("") })
        favViewModel.favouritesWithMatchingTitle.observe(viewLifecycleOwner, { favListAdapter.notifyDataSetChanged() })

        return inflater.inflate(R.layout.fragment_general_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView which displays favourite movies and TV shows:
        recyclerView = rv_generalList.apply {
            this.layoutManager = myLayoutManager
            this.adapter = favListAdapter
        }

        // Search view responsible for finding movies and TV shows with matching title:
        sv_generalList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(givenText : String) : Boolean {
                favViewModel.setFavouritesWithMatchingTitle(givenText)
                return false
            }

            override fun onQueryTextSubmit(givenText: String): Boolean {
                favViewModel.setFavouritesWithMatchingTitle(givenText)
                return false
            }
        })
    }
}