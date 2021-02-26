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
import com.example.popcorn.viewmodel.FavouriteViewModel
import com.example.popcorn.viewmodel.PersonViewModel
import com.example.popcorn.viewmodel.TVShowViewModel
import com.example.popcorn.viewmodel.adapters.details.PeopleInMovieAndTVShowAdapter
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*

// Fragment which is displayed after clicking specific TV show's row/column:
class TVShowDetailsFragment : Fragment() {
    // ViewModels:
    private lateinit var tvShowViewModel : TVShowViewModel
    private lateinit var personViewModel : PersonViewModel
    private lateinit var companyViewModel : CompanyViewModel
    private lateinit var favouriteViewModel : FavouriteViewModel

    // Adapters and their RecyclerViews:
    private lateinit var castAdapter : PeopleInMovieAndTVShowAdapter
    private lateinit var crewAdapter : PeopleInMovieAndTVShowAdapter
    private lateinit var castLayoutManager : LinearLayoutManager
    private lateinit var crewLayoutManager : LinearLayoutManager
    private lateinit var castRecyclerView : RecyclerView
    private lateinit var crewRecyclerView : RecyclerView


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // ViewModels:
        tvShowViewModel = ViewModelProvider(requireActivity()).get(TVShowViewModel::class.java)
        personViewModel = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)
        companyViewModel = ViewModelProvider(requireActivity()).get(CompanyViewModel::class.java)
        favouriteViewModel = ViewModelProvider(requireActivity()).get(FavouriteViewModel::class.java)

        // LinearLayoutManagers (used by RecyclerViews):
        castLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        crewLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Adapters (used by RecyclerViews):
        castAdapter = PeopleInMovieAndTVShowAdapter(tvShowViewModel.currentTVShowCast,
                personViewModel, "TVShow", "cast")
        crewAdapter = PeopleInMovieAndTVShowAdapter(tvShowViewModel.currentTVShowCrew,
                personViewModel, "TVShow", "crew")

        // Declaration of view:
        val view =  inflater.inflate(R.layout.fragment_details, container, false)

        // Description settings:
        view.tv_description.justificationMode = (LineBreaker.JUSTIFICATION_MODE_INTER_WORD)

        // Headers:
        view.tv_header1.text = resources.getString(R.string.descriptionHeader)
        view.tv_header2.text = resources.getString(R.string.castHeader)
        view.tv_header3.text = resources.getString(R.string.crewHeader)

        // Updating fragment after receiving response from API:
        tvShowViewModel.currentTVShow.observe(viewLifecycleOwner, {
            // Title:
            view.tv_titleOrName.text = it.name

            // Release date:
            if (!it.first_air_date.isNullOrEmpty())
            {
                view.tv_year.text = "First episode air date: ${it.first_air_date}"
                view.tv_year.visibility = View.VISIBLE
            }
            else view.tv_year.visibility = View.GONE

            // Runtime:
            if (!it.episode_run_time.isNullOrEmpty())
            {
                var runtimeSum = 0
                it.episode_run_time.forEach { x -> runtimeSum += x }
                view.tv_runtime.text = "Episode runtime: ${runtimeSum / it.episode_run_time.size} minutes"
                view.tv_runtime.visibility = View.VISIBLE
            }
            else view.tv_runtime.visibility = View.GONE

            // Genres (main two):
            if (!it.genres.isNullOrEmpty())
            {
                var genresText = ""
                var i = 0
                while (i < it.genres.size)
                {
                    if (i == 2) break
                    else genresText += it.genres[i].name + ", "

                    i += 1
                }
                view.tv_genresOrKnownFor.text = "Genres: ${genresText.slice(IntRange(0, genresText.length - 3))}"
                view.tv_genresOrKnownFor.visibility = View.VISIBLE
            }
            else view.tv_genresOrKnownFor.visibility = View.GONE

            // Languages (main two but with "..." if there are more):
            if (!it.spoken_languages.isNullOrEmpty())
            {
                var languagesText = ""
                var i = 0
                while (i < it.spoken_languages.size)
                {
                    if (i == 2)
                    {
                        languagesText += "..." + ", "
                        break
                    }
                    else languagesText += it.spoken_languages[i].english_name + ", "

                    i += 1
                }
                view.tv_origin.text = "Languages: ${languagesText.slice(IntRange(0, languagesText.length - 3))}"
                view.tv_origin.visibility = View.VISIBLE
            }
            else view.tv_origin.visibility = View.GONE

            // Main company:
            if (!it.production_companies.isNullOrEmpty())
            {
                val currentCompany = it.production_companies[0]
                view.tv_mainCompany.text = "Company: ${currentCompany.name}"
                view.tv_mainCompany.visibility = View.VISIBLE
                view.tv_mainCompany.setOnClickListener {
                    companyViewModel.setCurrentCompany(currentCompany.id)
                    view.findNavController().navigate(R.id.action_TVShowDetailsFragment_to_companyDetailsFragment)
                }
            }
            else view.tv_mainCompany.visibility = View.GONE

            // Poster:
            val url = "https://image.tmdb.org/t/p/w185${it.poster_path}"
            Glide.with(view.iv_posterOrPhoto).load(url).centerCrop().placeholder(R.drawable.ic_twotone_live_tv_24holder).into(view.iv_posterOrPhoto)

            // Average vote:
            if (it.vote_average.toString().isNotEmpty())
            {
                view.tv_avgVote.text = "Rating: ${it.vote_average}/10.0"
                view.tv_avgVote.visibility = View.VISIBLE
            }
            else view.tv_avgVote.visibility = View.GONE

            // Buttons responsible for adding to favourites and deleting from favourites - updating view after any change in local database:
            favouriteViewModel.favourites.observe(viewLifecycleOwner, {

                // Adding to favourites:
                view.btn_addToFav.setOnClickListener {
                    tvShowViewModel.currentTVShow.value?.let { item -> favouriteViewModel.addFavourite(item) } }

                // Default visibility:
                view.btn_addToFav.visibility = View.VISIBLE
                view.btn_delFromFav.visibility = View.GONE

                // Deleting from favourites:
                val favouriteMovie = favouriteViewModel.favourites.value?.find {
                        x -> x.media_type == "tv" && x.movieOrTVShowID == tvShowViewModel.currentTVShow.value?.id }
                if (favouriteMovie != null)
                {
                    view.btn_delFromFav.setOnClickListener {
                        favouriteViewModel.deleteFavorite(favouriteMovie.id)
                        view.btn_addToFav.visibility = View.VISIBLE
                        view.btn_delFromFav.visibility = View.GONE
                    }

                    // Swapping visibility (deleting must be active, not adding):
                    view.btn_addToFav.visibility = View.GONE
                    view.btn_delFromFav.visibility = View.VISIBLE
                }
            })

            // Description:
            if (it.overview.isNullOrEmpty())
            {
                view.tv_header1.visibility = View.GONE
                view.tv_description.visibility = View.GONE
            }
            else
            {
                view.tv_description.text = it.overview
                view.tv_description.visibility = View.VISIBLE
                view.tv_header1.visibility = View.VISIBLE
            }

            // Updating data of people from the cast and the crew:
            tvShowViewModel.setPeopleConnectedWithCurrentTVShow(it.id)
        })

        // Updating cast's RecyclerView after receiving response from API:
        tvShowViewModel.currentTVShowCast.observe(viewLifecycleOwner, {
            castAdapter.notifyDataSetChanged()

            // Making sure that everything is well displayed:
            if (tvShowViewModel.currentTVShowCast.value.isNullOrEmpty())
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

        // Updating crew's RecyclerView after receiving response from API:
        tvShowViewModel.currentTVShowCrew.observe(viewLifecycleOwner, {
            crewAdapter.notifyDataSetChanged()

            // Making sure that everything is well displayed:
            if (tvShowViewModel.currentTVShowCrew.value.isNullOrEmpty())
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

        // RecyclerView which displays cast of current TV Show:
        castRecyclerView = rv_actorsInMovie.apply {
            this.layoutManager = castLayoutManager
            this.adapter = castAdapter
        }

        // RecyclerView which displays crew of current TVShow:
        crewRecyclerView = rv_crewInMovie.apply {
            this.layoutManager = crewLayoutManager
            this.adapter = crewAdapter
        }
    }
}