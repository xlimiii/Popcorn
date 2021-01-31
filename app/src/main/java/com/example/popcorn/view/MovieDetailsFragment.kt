package com.example.popcorn.view

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popcorn.R
import com.example.popcorn.model.Movie
import com.example.popcorn.viewmodel.MovieViewModel
import com.example.popcorn.viewmodel.adapters.MovieDetailsAdapter
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_movie_details.view.*

class MovieDetailsFragment : Fragment() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieDetailsAdapter: MovieDetailsAdapter
    private lateinit var myLayoutManager : LinearLayoutManager
    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        myLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        movieViewModel.currentMovie.value?.id?.let {
            movieViewModel.setPeopleConnectedWithCurrentMovie(
                it
            )
        }
        val view =  inflater.inflate(R.layout.fragment_movie_details, container, false)
        view.tv_movieDescription.setJustificationMode((JUSTIFICATION_MODE_INTER_WORD))
        movieDetailsAdapter= MovieDetailsAdapter(movieViewModel.peopleConnectedWithCurrentMovie)
        movieViewModel.currentMovie.observe(viewLifecycleOwner, {it ->
            view.tv_movieDetailsTitle.text = it.title
            view.tv_movieDescription.text = it.overview
            view.tv_year.text = it.release_date
            var genresText: String = " "
            it.genres?.forEach { x -> genresText+= x.name+" "}
            view.tv_genresForMovie.text = genresText
            view.tv_oryginalLang.text = "Original language: " + it.original_language
            val url = "https://image.tmdb.org/t/p/w185${it.poster_path}"
            Glide.with(view.iv_movieDetailsPoster)
                .load(url)
                .centerCrop()
                .into(view.iv_movieDetailsPoster)
            movieViewModel.setPeopleConnectedWithCurrentMovie(it.id)
        })
        movieViewModel.peopleConnectedWithCurrentMovie!!.observe(viewLifecycleOwner, {movieDetailsAdapter.notifyDataSetChanged()})


        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = rv_actorsInMovie.apply {
            this.layoutManager = myLayoutManager
            this.adapter = movieDetailsAdapter
        }





    }
    companion object { fun newInstance() = MovieDetailsFragment() }
}