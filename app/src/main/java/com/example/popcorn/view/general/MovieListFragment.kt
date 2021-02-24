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
import com.example.popcorn.viewmodel.adapters.general.MovieListAdapter
import kotlinx.android.synthetic.main.fragment_general_list.*

// Fragment displayed in second tab:
class MovieListFragment : Fragment() {
    // ViewModels:
    private lateinit var movieViewModel : MovieViewModel
    private lateinit var favViewModel : FavouriteViewModel

    // Adapter and its RecyclerView:
    private lateinit var movieListAdapter : MovieListAdapter
    private lateinit var myLayoutManager : LinearLayoutManager
    private lateinit var recyclerView : RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?): View? {
        // ViewModels:
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        favViewModel = ViewModelProvider(requireActivity()).get(FavouriteViewModel::class.java)

        // LinearLayoutManager (used by RecyclerView):
        myLayoutManager = LinearLayoutManager(context)

        // Initializing list of popular movies (by empty input):
        movieViewModel.setMoviesWithMatchingTitle("")

        // Adapter (used by RecyclerView):
        movieListAdapter = MovieListAdapter(movieViewModel.moviesWithMatchingTitle, movieViewModel, favViewModel, "MovieListFragment")

        // Updating movies' RecyclerView after receiving response from API or new data from local database:
        movieViewModel.moviesWithMatchingTitle.observe(viewLifecycleOwner, { movieListAdapter.notifyDataSetChanged() })
        favViewModel.favourites.observe(viewLifecycleOwner, { movieListAdapter.notifyDataSetChanged() })

        return inflater.inflate(R.layout.fragment_general_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView which displays popular movies or movies with matching title:
        recyclerView = rv_generalList.apply {
            this.layoutManager = myLayoutManager
            this.adapter = movieListAdapter
        }

        // Search view responsible for finding movies with matching title:
        sv_generalList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(givenText : String) : Boolean {
                movieViewModel.setMoviesWithMatchingTitle(givenText)
                return false
            }

            override fun onQueryTextSubmit(givenText: String): Boolean {
                movieViewModel.setMoviesWithMatchingTitle(givenText)
                return false
            }
        })
    }
}