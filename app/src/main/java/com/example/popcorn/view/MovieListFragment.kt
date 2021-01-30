package com.example.popcorn.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.model.Movie
import com.example.popcorn.viewmodel.FavouriteViewModel
import com.example.popcorn.viewmodel.MovieViewModel
import com.example.popcorn.viewmodel.adapters.MovieListAdapter
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : Fragment() {
    private lateinit var movieListAdapter : MovieListAdapter
    private lateinit var myLayoutManager : LinearLayoutManager
    private lateinit var recyclerView : RecyclerView
    private lateinit var movieVM : MovieViewModel
    private lateinit var favVM : FavouriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle?): View? {
        myLayoutManager = LinearLayoutManager(context)
        movieVM = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        favVM = ViewModelProvider(requireActivity()).get(FavouriteViewModel::class.java)

        movieVM.setMoviesWithMatchingTitle("")
        movieListAdapter = MovieListAdapter(movieVM.moviesWithMatchingTitle, movieVM, favVM)
        movieVM.moviesWithMatchingTitle.observe(viewLifecycleOwner, { movieListAdapter.notifyDataSetChanged() })

        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = rv_movieList.apply {
            this.layoutManager = myLayoutManager
            this.adapter = movieListAdapter
        }

        sv_movieList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(givenText : String) : Boolean { movieVM.setMoviesWithMatchingTitle(givenText); return false }
            override fun onQueryTextSubmit(query: String): Boolean { return false }
        })
    }

    companion object { fun newInstance() = MovieListFragment() }
}