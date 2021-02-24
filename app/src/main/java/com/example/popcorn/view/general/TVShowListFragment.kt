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
import com.example.popcorn.viewmodel.TVShowViewModel
import com.example.popcorn.viewmodel.adapters.general.TVShowListAdapter
import kotlinx.android.synthetic.main.fragment_general_list.*

// Fragment displayed in fourth tab:
class TVShowListFragment : Fragment() {
    // ViewModels:
    private lateinit var tvShowViewModel : TVShowViewModel
    private lateinit var favViewModel : FavouriteViewModel

    // Adapter and its RecyclerView:
    private lateinit var tvShowListAdapter : TVShowListAdapter
    private lateinit var myLayoutManager : LinearLayoutManager
    private lateinit var recyclerView : RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // ViewModels:
        tvShowViewModel = ViewModelProvider(requireActivity()).get(TVShowViewModel::class.java)
        favViewModel = ViewModelProvider(requireActivity()).get(FavouriteViewModel::class.java)

        // LinearLayoutManager (used by RecyclerView):
        myLayoutManager = LinearLayoutManager(context)

        // Initializing list of popular TV Shows (by empty input):
        tvShowViewModel.setTVShowsWithMatchingTitle("")

        // Adapter (used by RecyclerView):
        tvShowListAdapter = TVShowListAdapter(tvShowViewModel.TVShowsWithMatchingTitle, tvShowViewModel, favViewModel, "TVShowListFragment")

        // Updating TV Shows' RecyclerView after receiving response from API or new data from local database:
        tvShowViewModel.TVShowsWithMatchingTitle.observe(viewLifecycleOwner, { tvShowListAdapter.notifyDataSetChanged() })
        favViewModel.favourites.observe(viewLifecycleOwner, { tvShowListAdapter.notifyDataSetChanged() })

        return inflater.inflate(R.layout.fragment_general_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView which displays popular TV Shows or TV Shows with matching title:
        recyclerView = rv_generalList.apply {
            this.layoutManager = myLayoutManager
            this.adapter = tvShowListAdapter
        }

        // Search view responsible for finding TV Shows with matching title:
        sv_generalList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(givenText : String) : Boolean {
                tvShowViewModel.setTVShowsWithMatchingTitle(givenText)
                return false
            }

            override fun onQueryTextSubmit(givenText: String): Boolean {
                tvShowViewModel.setTVShowsWithMatchingTitle(givenText)
                return false
            }
        })
    }
}