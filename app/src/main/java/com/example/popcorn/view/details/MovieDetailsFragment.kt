package com.example.popcorn.view.details

import android.annotation.SuppressLint
import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popcorn.R
import com.example.popcorn.viewmodel.MovieViewModel
import com.example.popcorn.viewmodel.PersonViewModel
import com.example.popcorn.viewmodel.adapters.details.PeopleInMovieAndTVShowAdapter
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*

class MovieDetailsFragment : Fragment() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var personViewModel: PersonViewModel

    private lateinit var castAdapter: PeopleInMovieAndTVShowAdapter
    private lateinit var crewAdapter: PeopleInMovieAndTVShowAdapter

    private lateinit var castLayoutManager : LinearLayoutManager
    private lateinit var crewLayoutManager : LinearLayoutManager
    private lateinit var castRecyclerView : RecyclerView
    private lateinit var crewRecyclerView : RecyclerView

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        castLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        crewLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        personViewModel = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)

        castAdapter = PeopleInMovieAndTVShowAdapter(movieViewModel.currentMovieCast,
                personViewModel, "movie", "cast")
        crewAdapter = PeopleInMovieAndTVShowAdapter(movieViewModel.currentMovieCrew,
                personViewModel, "movie", "crew")

        val view =  inflater.inflate(R.layout.fragment_details, container, false)
        view.tv_movieDescription.justificationMode = (LineBreaker.JUSTIFICATION_MODE_INTER_WORD)
        view.tv_header1.text = "Description"
        view.tv_header2.text = "Cast"
        view.tv_header3.text = "Crew"

        movieViewModel.currentMovie.observe(viewLifecycleOwner, {
            view.tv_movieDetailsTitle.text = it.title
            view.tv_movieDescription.text = it.overview
            view.tv_year.text = it.release_date

            var genresText = ""
            it.genres.forEach { x -> genresText += x.name + " "}
            view.tv_genresForMovie.text = genresText

            var languagesText = ""
            it.spoken_languages.forEach{ x -> languagesText += x.english_name + " "}
            view.tv_oryginalLang.text = "Languages: $languagesText"

            val url = "https://image.tmdb.org/t/p/w185${it.poster_path}"
            Glide.with(view.iv_movieDetailsPoster).load(url).centerCrop().into(view.iv_movieDetailsPoster)

            movieViewModel.setPeopleConnectedWithCurrentMovie(it.id)
        })

        movieViewModel.currentMovieCast.observe(viewLifecycleOwner, { castAdapter.notifyDataSetChanged() })
        movieViewModel.currentMovieCrew.observe(viewLifecycleOwner, { crewAdapter.notifyDataSetChanged() })

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        castRecyclerView = rv_actorsInMovie.apply {
            this.layoutManager = castLayoutManager
            this.adapter = castAdapter
        }

        crewRecyclerView = rv_crewInMovie.apply {
            this.layoutManager = crewLayoutManager
            this.adapter = crewAdapter
        }
    }
}