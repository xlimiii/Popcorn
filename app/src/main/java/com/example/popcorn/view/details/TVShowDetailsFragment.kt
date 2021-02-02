package com.example.popcorn.view.details

import android.annotation.SuppressLint
import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popcorn.R
import com.example.popcorn.viewmodel.PersonViewModel
import com.example.popcorn.viewmodel.TVShowViewModel
import com.example.popcorn.viewmodel.adapters.details.CrewInMovieAndTVShowAdapter
import com.example.popcorn.viewmodel.adapters.details.TVShowDetailsAdapter
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*

class TVShowDetailsFragment : Fragment() {
    private lateinit var tvsViewModel : TVShowViewModel
    private lateinit var personViewModel : PersonViewModel
    private lateinit var tvsDetailsAdapter : TVShowDetailsAdapter
    private lateinit var crewAdapter : CrewInMovieAndTVShowAdapter
    private lateinit var myLayoutManager : LinearLayoutManager
    private lateinit var recyclerView : RecyclerView
    private lateinit var myLayoutManager2 : LinearLayoutManager
    private lateinit var recyclerView2 : RecyclerView

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        myLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        myLayoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        tvsViewModel = ViewModelProvider(requireActivity()).get(TVShowViewModel::class.java)
        personViewModel = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)

        val view =  inflater.inflate(R.layout.fragment_details, container, false)
        view.tv_movieDescription.justificationMode = (LineBreaker.JUSTIFICATION_MODE_INTER_WORD)
        view.tv_header1.text = "Description"
        view.tv_header2.text = "Cast"
        view.tv_header3.text = "Crew"

        tvsDetailsAdapter = TVShowDetailsAdapter(tvsViewModel.currentTVShowCast, personViewModel)
        crewAdapter = CrewInMovieAndTVShowAdapter(tvsViewModel.currentTVShowCrew, personViewModel)

        tvsViewModel.currentTVShow.observe(viewLifecycleOwner, {
            view.tv_movieDetailsTitle.text = it.name
            view.tv_movieDescription.text = it.overview
            view.tv_year.text = it.first_air_date
            var genresText = ""
            var languagesText = ""

            it.genres.forEach { x -> genresText += x.name + " "}
            view.tv_genresForMovie.text = genresText
            it.spoken_languages.forEach{ x -> languagesText += x.english_name + " "}
            view.tv_oryginalLang.text = "Languages: $languagesText"
            val url = "https://image.tmdb.org/t/p/w185${it.poster_path}"
            Glide.with(view.iv_movieDetailsPoster).load(url).centerCrop().into(view.iv_movieDetailsPoster)
            tvsViewModel.setPeopleConnectedWithCurrentTVShow(it.id)
        })

        tvsViewModel.currentTVShowCast.observe(viewLifecycleOwner, { tvsDetailsAdapter.notifyDataSetChanged() })
        tvsViewModel.currentTVShowCast.observe(viewLifecycleOwner, { crewAdapter.notifyDataSetChanged() })

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = rv_actorsInMovie.apply {
            this.layoutManager = myLayoutManager
            this.adapter = tvsDetailsAdapter
        }
        recyclerView2 = rv_crewInMovie.apply {
            this.layoutManager = myLayoutManager2
            this.adapter = crewAdapter
        }
    }
    companion object { fun newInstance() = MovieDetailsFragment() }
}