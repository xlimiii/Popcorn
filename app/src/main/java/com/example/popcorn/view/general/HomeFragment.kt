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
import com.example.popcorn.viewmodel.*
import com.example.popcorn.viewmodel.adapters.general.PersonListAdapter
import com.example.popcorn.viewmodel.adapters.general.MovieListAdapter
import com.example.popcorn.viewmodel.adapters.general.TVShowListAdapter
import kotlinx.android.synthetic.main.fragment_home.*

// Fragment displayed in first tab:
class HomeFragment : Fragment() {
    private lateinit var personViewModel : PersonViewModel
    private lateinit var movieViewModel : MovieViewModel
    private lateinit var tvShowViewModel : TVShowViewModel
    private lateinit var favViewModel : FavouriteViewModel

    // Adapters and their RecyclerViews:
    private lateinit var movieListAdapter : MovieListAdapter
    private lateinit var personListAdapter : PersonListAdapter
    private lateinit var tvShowListAdapter: TVShowListAdapter
    private lateinit var movieLayoutManager  : LinearLayoutManager
    private lateinit var personLayoutManager : LinearLayoutManager
    private lateinit var tvShowLayoutManager : LinearLayoutManager
    private lateinit var movieRecyclerView  : RecyclerView
    private lateinit var personRecyclerView : RecyclerView
    private lateinit var tvShowRecyclerView : RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // ViewModels:
        personViewModel = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        favViewModel = ViewModelProvider(requireActivity()).get(FavouriteViewModel::class.java)
        tvShowViewModel = ViewModelProvider(requireActivity()).get(TVShowViewModel::class.java)

        // LinearLayoutManagers (used by RecyclerViews):
        movieLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        personLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        tvShowLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Initializing lists of popular movies / people / TV Shows (by empty input):
        movieViewModel.setMoviesWithMatchingTitle("")
        personViewModel.setPeopleWithMatchingName("")
        tvShowViewModel.setTVShowsWithMatchingTitle("")

        // Adapters (used by RecyclerViews):
        movieListAdapter = MovieListAdapter(movieViewModel.moviesWithMatchingTitle, movieViewModel, favViewModel, "HomeFragment")
        personListAdapter = PersonListAdapter(personViewModel.peopleWithMatchingName, personViewModel, "HomeFragment")
        tvShowListAdapter = TVShowListAdapter(tvShowViewModel.TVShowsWithMatchingTitle, tvShowViewModel, favViewModel,"HomeFragment")

        // Updating movies' RecyclerView after receiving response from API:
        movieViewModel.moviesWithMatchingTitle.observe(viewLifecycleOwner, {
            movieListAdapter.notifyDataSetChanged()

            // Making sure that everything is well displayed:
            if (movieViewModel.moviesWithMatchingTitle.value.isNullOrEmpty()) tv_homeHeader1.visibility = View.GONE
            else tv_homeHeader1.visibility = View.VISIBLE
        })

        // Updating people's RecyclerView after receiving response from API:
        personViewModel.peopleWithMatchingName.observe(viewLifecycleOwner, {
            personListAdapter.notifyDataSetChanged()

            // Making sure that everything is well displayed:
            if (personViewModel.peopleWithMatchingName.value.isNullOrEmpty()) tv_homeHeader2.visibility = View.GONE
            else tv_homeHeader2.visibility = View.VISIBLE
        })

        // Updating TVShows' RecyclerView after receiving response from API:
        tvShowViewModel.TVShowsWithMatchingTitle.observe(viewLifecycleOwner, {
            tvShowListAdapter.notifyDataSetChanged()

            // Making sure that everything is well displayed:
            if (tvShowViewModel.TVShowsWithMatchingTitle.value.isNullOrEmpty()) tv_homeHeader3.visibility = View.GONE
            else tv_homeHeader3.visibility = View.VISIBLE
        })

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView which displays popular movies or movies with matching title:
        movieRecyclerView = rv_multiList.apply {
            this.layoutManager = movieLayoutManager
            this.adapter = movieListAdapter
        }

        // RecyclerView which displays popular people or people with matching name:
        personRecyclerView = rv_multiList2.apply {
            this.layoutManager = personLayoutManager
            this.adapter = personListAdapter
        }

        // RecyclerView which displays popular TV Shows or TV Shows with matching title:
        tvShowRecyclerView = rv_multiList3.apply {
            this.layoutManager = tvShowLayoutManager
            this.adapter = tvShowListAdapter
        }

        // Search view responsible for finding movies, people and TV shows with matching title/name:
        sv_multiList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(givenText : String) : Boolean {
                movieViewModel.setMoviesWithMatchingTitle(givenText)
                personViewModel.setPeopleWithMatchingName(givenText)
                tvShowViewModel.setTVShowsWithMatchingTitle(givenText)
                return false
            }

            override fun onQueryTextSubmit(givenText: String): Boolean {
                movieViewModel.setMoviesWithMatchingTitle(givenText)
                personViewModel.setPeopleWithMatchingName(givenText)
                tvShowViewModel.setTVShowsWithMatchingTitle(givenText)
                return false
            }
        })
    }
}