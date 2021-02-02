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
import com.example.popcorn.viewmodel.adapters.general.ActorListAdapter
import com.example.popcorn.viewmodel.adapters.general.MovieListAdapter
import com.example.popcorn.viewmodel.adapters.general.TVShowListAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var movieListAdapter : MovieListAdapter
    private lateinit var personListAdapter : ActorListAdapter
    private lateinit var tvShowListAdapter: TVShowListAdapter

    private lateinit var myLayoutManager : LinearLayoutManager
    private lateinit var myLayoutManager2 : LinearLayoutManager
    private lateinit var myLayoutManager3 : LinearLayoutManager

    private lateinit var recyclerView : RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView

    private lateinit var personVM : PersonViewModel
    private lateinit var movieVM : MovieViewModel
    private lateinit var tvShowVM : TVShowViewModel
    private lateinit var favVM : FavouriteViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        myLayoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        myLayoutManager3 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        personVM = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)
        movieVM = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        favVM = ViewModelProvider(requireActivity()).get(FavouriteViewModel::class.java)
        tvShowVM = ViewModelProvider(requireActivity()).get(TVShowViewModel::class.java)

        movieVM.setMoviesWithMatchingTitle("")
        personVM.setPeopleWithMatchingName("")
        tvShowVM.setTVShowsWithMatchingTitle("")

        movieListAdapter = MovieListAdapter(movieVM.moviesWithMatchingTitle, movieVM, favVM, 1)
        personListAdapter = ActorListAdapter(personVM.peopleWithMatchingName, personVM, 1)
        tvShowListAdapter = TVShowListAdapter(tvShowVM.TVShowsWithMatchingTitle, tvShowVM, favVM,1)


        movieVM.moviesWithMatchingTitle.observe(viewLifecycleOwner, { movieListAdapter.notifyDataSetChanged() })
        personVM.peopleWithMatchingName.observe(viewLifecycleOwner, { personListAdapter.notifyDataSetChanged() })
        tvShowVM.TVShowsWithMatchingTitle.observe(viewLifecycleOwner, { tvShowListAdapter.notifyDataSetChanged() })


        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = rv_multiList.apply {
            this.layoutManager = myLayoutManager
            this.adapter = movieListAdapter
        }
        recyclerView2 = rv_multiList2.apply {
            this.layoutManager = myLayoutManager2
            this.adapter = personListAdapter
        }
        recyclerView3 = rv_multiList3.apply {
            this.layoutManager = myLayoutManager3
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

    companion object { fun newInstance() = HomeFragment() }
}