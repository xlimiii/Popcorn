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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popcorn.R
import com.example.popcorn.viewmodel.CompanyViewModel
import com.example.popcorn.viewmodel.PersonViewModel
import com.example.popcorn.viewmodel.TVShowViewModel
import com.example.popcorn.viewmodel.adapters.details.PeopleInMovieAndTVShowAdapter
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*

class TVShowDetailsFragment : Fragment() {
    private lateinit var tvsViewModel : TVShowViewModel
    private lateinit var personViewModel : PersonViewModel
    private lateinit var companyViewModel: CompanyViewModel

    private lateinit var castAdapter : PeopleInMovieAndTVShowAdapter
    private lateinit var crewAdapter : PeopleInMovieAndTVShowAdapter

    private lateinit var castLayoutManager : LinearLayoutManager
    private lateinit var crewLayoutManager : LinearLayoutManager
    private lateinit var castRecyclerView : RecyclerView
    private lateinit var crewRecyclerView : RecyclerView

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        castLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        crewLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        tvsViewModel = ViewModelProvider(requireActivity()).get(TVShowViewModel::class.java)
        personViewModel = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)
        companyViewModel = ViewModelProvider(requireActivity()).get(CompanyViewModel::class.java)

        castAdapter = PeopleInMovieAndTVShowAdapter(tvsViewModel.currentTVShowCast,
                personViewModel, "TVShow", "cast")
        crewAdapter = PeopleInMovieAndTVShowAdapter(tvsViewModel.currentTVShowCrew,
                personViewModel, "TVShow", "crew")

        val view =  inflater.inflate(R.layout.fragment_details, container, false)
        view.tv_movieDescription.justificationMode = (LineBreaker.JUSTIFICATION_MODE_INTER_WORD)
        view.tv_header1.text = "Description"
        view.tv_header2.text = "Cast"
        view.tv_header3.text = "Crew"

        tvsViewModel.currentTVShow.observe(viewLifecycleOwner, {
            // title:
            view.tv_movieDetailsTitle.text = it.name

            // release date:
            if (!it.first_air_date.isNullOrEmpty())
            {
                view.tv_year.text = it.first_air_date
                view.tv_year.visibility = View.VISIBLE
            }
            else view.tv_year.visibility = View.GONE

            // genres:
            if (!it.genres.isNullOrEmpty())
            {
                var genresText = ""
                it.genres.forEach { x -> genresText += x.name + " "}
                view.tv_genresForMovie.text = "Genres: $genresText"
                view.tv_genresForMovie.visibility = View.VISIBLE
            }
            else view.tv_genresForMovie.visibility = View.GONE

            // languages:
            if (!it.spoken_languages.isNullOrEmpty())
            {
                var languagesText = ""
                it.spoken_languages.forEach{ x -> languagesText += x.english_name + " "}
                view.tv_oryginalLang.text = "Languages: $languagesText"
                view.tv_oryginalLang.visibility = View.VISIBLE
            }
            else view.tv_oryginalLang.visibility = View.GONE

            // main company:
            if (!it.production_companies.isNullOrEmpty())
            {
                val currentCompany = it.production_companies[0]
                view.tv_mainCompany.text = "Main company: ${currentCompany.name}"
                view.tv_mainCompany.visibility = View.VISIBLE
                view.tv_mainCompany.setOnClickListener {
                    companyViewModel.setCurrentCompany(currentCompany.id)
                    view.findNavController().navigate(R.id.action_TVShowDetailsFragment_to_companyDetailsFragment)
                }
            }
            else view.tv_mainCompany.visibility = View.GONE

            // poster:
            val url = "https://image.tmdb.org/t/p/w185${it.poster_path}"
            Glide.with(view.iv_movieDetailsPoster).load(url).centerCrop().placeholder(R.drawable.ic_twotone_live_tv_24holder).into(view.iv_movieDetailsPoster)

            // description:
            if (it.overview.isNullOrEmpty())
            {
                view.tv_header1.visibility = View.GONE
                view.tv_movieDescription.visibility = View.GONE
            }
            else
            {
                view.tv_movieDescription.text = it.overview
                view.tv_movieDescription.visibility = View.VISIBLE
                view.tv_header1.visibility = View.VISIBLE
            }

            tvsViewModel.setPeopleConnectedWithCurrentTVShow(it.id)
        })

        // cast:
        tvsViewModel.currentTVShowCast.observe(viewLifecycleOwner, {
            castAdapter.notifyDataSetChanged()
            if (tvsViewModel.currentTVShowCast.value.isNullOrEmpty())
            {
                view.tv_header2.visibility = View.GONE
                view.rv_actorsInMovie.visibility = View.GONE
            }
            else
            {
                view.tv_header2.visibility = View.VISIBLE
                view.rv_actorsInMovie.visibility = View.VISIBLE
            }
        })

        // crew:
        tvsViewModel.currentTVShowCrew.observe(viewLifecycleOwner, {
            crewAdapter.notifyDataSetChanged()
            if (tvsViewModel.currentTVShowCrew.value.isNullOrEmpty())
            {
                view.tv_header3.visibility = View.GONE
                view.rv_crewInMovie.visibility = View.GONE
            }
            else
            {
                view.tv_header3.visibility = View.VISIBLE
                view.rv_crewInMovie.visibility = View.VISIBLE
            }
        })

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