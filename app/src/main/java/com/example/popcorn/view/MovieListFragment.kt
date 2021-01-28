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
import com.example.popcorn.viewmodel.MovieViewModel
import com.example.popcorn.viewmodel.adapters.MovieListAdapter
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : Fragment() {
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        myLayoutManager= LinearLayoutManager(context)

        viewModel= ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)

        movieListAdapter= MovieListAdapter(viewModel.popularMovies)

        viewModel.popularMovies.observe(viewLifecycleOwner, Observer { t ->
            movieListAdapter.notifyDataSetChanged()
        })
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=rv_movieList.apply {
            this.layoutManager=myLayoutManager
            this.adapter=movieListAdapter
        }
    }

    companion object { fun newInstance() = MovieListFragment() }
}