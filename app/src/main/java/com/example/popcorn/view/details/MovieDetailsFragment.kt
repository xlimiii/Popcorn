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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popcorn.R
import com.example.popcorn.viewmodel.CompanyViewModel
import com.example.popcorn.viewmodel.FavouriteViewModel
import com.example.popcorn.viewmodel.MovieViewModel
import com.example.popcorn.viewmodel.PersonViewModel
import com.example.popcorn.viewmodel.adapters.details.PeopleInMovieAndTVShowAdapter
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*

// Fragment which is displayed after clicking specific movie's row/column:
class MovieDetailsFragment : Fragment() {
    // ViewModels:
    private lateinit var movieViewModel : MovieViewModel
    private lateinit var personViewModel : PersonViewModel
    private lateinit var companyViewModel : CompanyViewModel
    private lateinit var favouriteViewModel : FavouriteViewModel

    // Adapters and their RecyclerViews:
    private lateinit var castAdapter: PeopleInMovieAndTVShowAdapter
    private lateinit var crewAdapter: PeopleInMovieAndTVShowAdapter
    private lateinit var castLayoutManager : LinearLayoutManager
    private lateinit var crewLayoutManager : LinearLayoutManager
    private lateinit var castRecyclerView : RecyclerView
    private lateinit var crewRecyclerView : RecyclerView


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // ViewModels:
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        personViewModel = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)
        companyViewModel = ViewModelProvider(requireActivity()).get(CompanyViewModel::class.java)
        favouriteViewModel = ViewModelProvider(requireActivity()).get(FavouriteViewModel::class.java)

        // LinearLayoutManagers (used by RecyclerViews):
        castLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        crewLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Adapters (used by RecyclerViews):
        castAdapter = PeopleInMovieAndTVShowAdapter(movieViewModel.currentMovieCast,
                personViewModel, "movie", "cast")
        crewAdapter = PeopleInMovieAndTVShowAdapter(movieViewModel.currentMovieCrew,
                personViewModel, "movie", "crew")

        // Declaration of view:
        val view =  inflater.inflate(R.layout.fragment_details, container, false)

        // Description settings:
        view.tv_description.justificationMode = (LineBreaker.JUSTIFICATION_MODE_INTER_WORD)

        // Headers:
        view.tv_header1.text = resources.getString(R.string.descriptionHeader)
        view.tv_header2.text = resources.getString(R.string.castHeader)
        view.tv_header3.text = resources.getString(R.string.crewHeader)

        // Updating fragment after receiving response from API:
        movieViewModel.currentMovie.observe(viewLifecycleOwner, {
            // Title:
            view.tv_titleOrName.text = it.title

            // Release date:
            if (!it.release_date.isNullOrEmpty())
            {
                view.tv_year.text = "Release date: ${it.release_date}"
                view.tv_year.visibility = View.VISIBLE
            }
            else view.tv_year.visibility = View.GONE

            // Genres:
            if (!it.genres.isNullOrEmpty())
            {
                var genresText = ""
                it.genres.forEach { x -> genresText += x.name + ", "}
                view.tv_genresOrKnownFor.text = "Genres: ${genresText.slice(IntRange(0, genresText.length - 3))}"
                view.tv_genresOrKnownFor.visibility = View.VISIBLE
            }
            else view.tv_genresOrKnownFor.visibility = View.GONE

            // Languages:
            if (!it.spoken_languages.isNullOrEmpty())
            {
                var languagesText = ""
                it.spoken_languages.forEach{ x -> languagesText += x.english_name + " "}
                view.tv_origin.text = "Languages: $languagesText"
                view.tv_origin.visibility = View.VISIBLE
            }
            else view.tv_origin.visibility = View.GONE

            // Main company:
            if (!it.production_companies.isNullOrEmpty())
            {
                val currentCompany = it.production_companies[0]
                view.tv_mainCompany.text = "Main company: ${currentCompany.name}"
                view.tv_mainCompany.visibility = View.VISIBLE
                view.tv_mainCompany.setOnClickListener {
                    companyViewModel.setCurrentCompany(currentCompany.id)
                    view.findNavController().navigate(R.id.action_movieDetailsFragment_to_companyDetailsFragment)
                }
            }
            else view.tv_mainCompany.visibility = View.GONE

            // Poster:
            val url = "https://image.tmdb.org/t/p/w185${it.poster_path}"
            Glide.with(view.iv_posterOrPhoto).load(url).centerCrop().placeholder(R.drawable.ic_outline_movie_24holder).into(view.iv_posterOrPhoto)

            // Buttons responsible for adding to favourites and deleting from favourites - updating view after any change in local database:
            favouriteViewModel.favourites.observe(viewLifecycleOwner, {

                // Adding to favourites:
                view.btn_addToFav.setOnClickListener {
                    movieViewModel.currentMovie.value?.let { item -> favouriteViewModel.addFavourite(item) } }

                // Default visibility:
                view.btn_addToFav.visibility = View.VISIBLE
                view.btn_delFromFav.visibility = View.GONE

                // Deleting from favourites:
                val favouriteMovie = favouriteViewModel.favourites.value?.find {
                        x -> x.media_type == "movie" && x.movieOrTVShowID == movieViewModel.currentMovie.value?.id }
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
            movieViewModel.setPeopleConnectedWithCurrentMovie(it.id)
        })

        // Updating cast's RecyclerView after receiving response from API:
        movieViewModel.currentMovieCast.observe(viewLifecycleOwner, {
            castAdapter.notifyDataSetChanged()

            // Making sure that everything is well displayed:
            if (movieViewModel.currentMovieCast.value.isNullOrEmpty())
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
        movieViewModel.currentMovieCrew.observe(viewLifecycleOwner, {
            crewAdapter.notifyDataSetChanged()

            // Making sure that everything is well displayed:
            if (movieViewModel.currentMovieCrew.value.isNullOrEmpty())
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

        // RecyclerView which displays cast of current movie:
        castRecyclerView = rv_actorsInMovie.apply {
            this.layoutManager = castLayoutManager
            this.adapter = castAdapter
        }

        // RecyclerView which displays crew of current movie:
        crewRecyclerView = rv_crewInMovie.apply {
            this.layoutManager = crewLayoutManager
            this.adapter = crewAdapter
        }
    }
}