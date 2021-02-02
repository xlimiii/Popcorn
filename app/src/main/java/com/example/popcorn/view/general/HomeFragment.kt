package com.example.popcorn.view.general

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.viewmodel.*
import com.example.popcorn.viewmodel.adapters.general.PersonListAdapter
import com.example.popcorn.viewmodel.adapters.general.MovieListAdapter
import com.example.popcorn.viewmodel.adapters.general.TVShowListAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var movieListAdapter : MovieListAdapter
    private lateinit var personListAdapter : PersonListAdapter
    private lateinit var tvShowListAdapter: TVShowListAdapter

    private lateinit var movieLayoutManager  : LinearLayoutManager
    private lateinit var personLayoutManager : LinearLayoutManager
    private lateinit var tvShowLayoutManager : LinearLayoutManager

    private lateinit var movieRecyclerView  : RecyclerView
    private lateinit var personRecyclerView : RecyclerView
    private lateinit var tvShowRecyclerView : RecyclerView

    private lateinit var personVM : PersonViewModel
    private lateinit var movieVM : MovieViewModel
    private lateinit var tvShowVM : TVShowViewModel
    private lateinit var favVM : FavouriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        movieLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        personLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        tvShowLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        personVM = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)
        movieVM = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        favVM = ViewModelProvider(requireActivity()).get(FavouriteViewModel::class.java)
        tvShowVM = ViewModelProvider(requireActivity()).get(TVShowViewModel::class.java)

        // Initialize lists of popular movies / people / TV Shows (empty input)
        movieVM.setMoviesWithMatchingTitle("")
        personVM.setPeopleWithMatchingName("")
        tvShowVM.setTVShowsWithMatchingTitle("")

        movieListAdapter = MovieListAdapter(movieVM.moviesWithMatchingTitle, movieVM, favVM, "HomeFragment")
        personListAdapter = PersonListAdapter(personVM.peopleWithMatchingName, personVM, "HomeFragment")
        tvShowListAdapter = TVShowListAdapter(tvShowVM.TVShowsWithMatchingTitle, tvShowVM, favVM,"HomeFragment")

        movieVM.moviesWithMatchingTitle.observe(viewLifecycleOwner, { movieListAdapter.notifyDataSetChanged() })
        personVM.peopleWithMatchingName.observe(viewLifecycleOwner, { personListAdapter.notifyDataSetChanged() })
        tvShowVM.TVShowsWithMatchingTitle.observe(viewLifecycleOwner, { tvShowListAdapter.notifyDataSetChanged() })

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieRecyclerView = rv_multiList.apply {
            this.layoutManager = movieLayoutManager
            this.adapter = movieListAdapter
        }

        personRecyclerView = rv_multiList2.apply {
            this.layoutManager = personLayoutManager
            this.adapter = personListAdapter
        }

        tvShowRecyclerView = rv_multiList3.apply {
            this.layoutManager = tvShowLayoutManager
            this.adapter = tvShowListAdapter
        }

        sv_multiList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(givenText : String) : Boolean {
                movieVM.setMoviesWithMatchingTitle(givenText)
                personVM.setPeopleWithMatchingName(givenText)
                tvShowVM.setTVShowsWithMatchingTitle(givenText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean { return false }
        })
    }
}