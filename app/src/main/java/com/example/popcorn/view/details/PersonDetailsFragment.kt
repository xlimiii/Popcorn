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
import com.example.popcorn.viewmodel.TVShowViewModel
import com.example.popcorn.viewmodel.adapters.details.MoviesAndTVShowsInPersonAdapter
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*

class PersonDetailsFragment : Fragment() {
    private lateinit var inCastAdapter: MoviesAndTVShowsInPersonAdapter
    private lateinit var inCrewAdapter: MoviesAndTVShowsInPersonAdapter

    private lateinit var personViewModel : PersonViewModel
    private lateinit var movieViewModel : MovieViewModel
    private lateinit var tvShowViewModel : TVShowViewModel

    private lateinit var inCastLayoutManager : LinearLayoutManager
    private lateinit var inCrewLayoutManager : LinearLayoutManager
    private lateinit var inCastRecyclerView : RecyclerView
    private lateinit var inCrewRecyclerView : RecyclerView

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inCastLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        inCrewLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        personViewModel = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        tvShowViewModel = ViewModelProvider(requireActivity()).get(TVShowViewModel::class.java)

        inCastAdapter = MoviesAndTVShowsInPersonAdapter(personViewModel.currentPersonInCastCollection,
                movieViewModel, tvShowViewModel, "inCast")
        inCrewAdapter = MoviesAndTVShowsInPersonAdapter(personViewModel.currentPersonInCrewCollection,
                movieViewModel, tvShowViewModel, "inCrew")

        val view =  inflater.inflate(R.layout.fragment_details, container, false)
        view.tv_movieDescription.justificationMode = (LineBreaker.JUSTIFICATION_MODE_INTER_WORD)
        view.tv_header1.text = "Biography"
        view.tv_header2.text = "Performed in"
        view.tv_header3.text = "Crew of"

        personViewModel.currentPerson.observe(viewLifecycleOwner, {
            view.tv_movieDetailsTitle.text = it.name
            view.tv_movieDescription.text = it.biography
            view.tv_year.text = it.birthday
            view.tv_year2.text = it.deathday
            view.tv_genresForMovie.text = "Known for: " + it.known_for_department
            view.tv_oryginalLang.text = "Place of Birth: " + it.place_of_birth

            val url = "https://image.tmdb.org/t/p/w185${it.profile_path}"
            Glide.with(view.iv_movieDetailsPoster).load(url).centerCrop().into(view.iv_movieDetailsPoster)

            personViewModel.setCurrentPersonCollection(it.id)
        })

        personViewModel.currentPersonInCastCollection.observe(viewLifecycleOwner, { inCastAdapter.notifyDataSetChanged() })
        personViewModel.currentPersonInCrewCollection.observe(viewLifecycleOwner, { inCrewAdapter.notifyDataSetChanged() })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inCastRecyclerView = rv_actorsInMovie.apply {
            this.layoutManager = inCastLayoutManager
            this.adapter = inCastAdapter
        }

        inCrewRecyclerView = rv_crewInMovie.apply {
            this.layoutManager = inCrewLayoutManager
            this.adapter = inCrewAdapter
        }
    }
}