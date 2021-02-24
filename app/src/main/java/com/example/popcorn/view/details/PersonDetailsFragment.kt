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

// Fragment which is displayed after clicking specific person's row/column:
class PersonDetailsFragment : Fragment() {
    // ViewModels:
    private lateinit var personViewModel : PersonViewModel
    private lateinit var movieViewModel : MovieViewModel
    private lateinit var tvShowViewModel : TVShowViewModel

    // Adapters and their RecyclerViews:
    private lateinit var inCastAdapter: MoviesAndTVShowsInPersonAdapter
    private lateinit var inCrewAdapter: MoviesAndTVShowsInPersonAdapter
    private lateinit var inCastLayoutManager : LinearLayoutManager
    private lateinit var inCrewLayoutManager : LinearLayoutManager
    private lateinit var inCastRecyclerView : RecyclerView
    private lateinit var inCrewRecyclerView : RecyclerView


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // ViewModels:
        personViewModel = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        tvShowViewModel = ViewModelProvider(requireActivity()).get(TVShowViewModel::class.java)

        // LinearLayoutManagers (used by RecyclerViews):
        inCastLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        inCrewLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Adapters (used by RecyclerViews):
        inCastAdapter = MoviesAndTVShowsInPersonAdapter(personViewModel.currentPersonInCastCollection,
                movieViewModel, tvShowViewModel, "inCast")
        inCrewAdapter = MoviesAndTVShowsInPersonAdapter(personViewModel.currentPersonInCrewCollection,
                movieViewModel, tvShowViewModel, "inCrew")

        // Declaration of view:
        val view =  inflater.inflate(R.layout.fragment_details, container, false)

        // Biography settings:
        view.tv_description.justificationMode = (LineBreaker.JUSTIFICATION_MODE_INTER_WORD)

        // Headers:
        view.tv_header1.text = resources.getString(R.string.biographyHeader)
        view.tv_header2.text = resources.getString(R.string.inCastHeader)
        view.tv_header3.text = resources.getString(R.string.inCrewHeader)

        personViewModel.currentPerson.observe(viewLifecycleOwner, {
            // Name:
            view.tv_titleOrName.text = it.name

            // Biography:
            if (it.biography.isNullOrEmpty()) view.tv_description.visibility = View.GONE
            else
            {
                view.tv_header1.visibility = View.VISIBLE
                view.tv_description.text = it.biography
                view.tv_description.visibility = View.VISIBLE
            }

            // Date of birth:
            if (it.birthday.isNullOrEmpty())
                view.tv_year.visibility = View.GONE
            else
            {
                view.tv_year.text = "Born: ${it.birthday}"
                view.tv_year.visibility = View.VISIBLE
            }

            // Date of death:
            if (it.deathDay.isNullOrEmpty() || it.birthday.isNullOrEmpty())
                view.tv_year2.visibility = View.GONE
            else
            {
                view.tv_year2.text = "Died: ${it.deathDay}"
                view.tv_year2.visibility = View.VISIBLE
            }

            // Place of birth:
            view.tv_origin.text = "Place of Birth: " + it.place_of_birth
            if (it.place_of_birth.isNullOrEmpty()) view.tv_origin.visibility = View.GONE
            else view.tv_origin.visibility = View.VISIBLE

            // Department:
            view.tv_genresOrKnownFor.text = "Known for: " + it.known_for_department
            if (it.known_for_department.isNullOrEmpty()) view.tv_genresOrKnownFor.visibility = View.GONE
            else view.tv_genresOrKnownFor.visibility = View.VISIBLE

            // Photo:
            val url = "https://image.tmdb.org/t/p/w185${it.profile_path}"
            val placeholderImg : Int = when(it.gender) {
                2 -> R.drawable.ic_person_placeholder_24
                1 -> R.drawable.ic_person_placeholder_24_2
                else -> R.drawable.ic_person_placeholder_24_e
            }
            Glide.with(view.iv_posterOrPhoto).load(url).centerCrop().placeholder(placeholderImg).into(view.iv_posterOrPhoto)

            // Updating data of movie and TV shows that current person performed in or was in crew of:
            personViewModel.setCurrentPersonCollection(it.id)
        })

        // Updating inCast's RecyclerView after receiving response from API:
        personViewModel.currentPersonInCastCollection.observe(viewLifecycleOwner, {
            inCastAdapter.notifyDataSetChanged()

            // Making sure that everything is well displayed:
            if (personViewModel.currentPersonInCastCollection.value.isNullOrEmpty())
            {
                view.tv_header2.visibility = View.GONE
                view.rv_actorsInMovie.visibility = View.GONE
            }
            else
            {
                view.tv_header2.visibility = View.VISIBLE
                view.rv_actorsInMovie.visibility = View.VISIBLE

                if (personViewModel.currentPerson.value?.biography.isNullOrEmpty())
                {
                    view.tv_header1.text = view.tv_header2.text
                    view.tv_header2.visibility = View.GONE
                }
                else view.tv_header1.text = resources.getString(R.string.biographyHeader)
            }
        })

        // Updating inCrew's RecyclerView after receiving response from API:
        personViewModel.currentPersonInCrewCollection.observe(viewLifecycleOwner, {
            inCrewAdapter.notifyDataSetChanged()

            // Making sure that everything is well displayed:
            if (personViewModel.currentPersonInCrewCollection.value.isNullOrEmpty())
            {
                view.tv_header3.visibility = View.GONE
                view.rv_crewInMovie.visibility = View.GONE
            }
            else
            {
                view.tv_header3.visibility = View.VISIBLE
                view.rv_crewInMovie.visibility = View.VISIBLE

                if (personViewModel.currentPerson.value?.biography.isNullOrEmpty()
                    && personViewModel.currentPersonInCastCollection.value.isNullOrEmpty())
                {
                    view.tv_header1.text = view.tv_header3.text
                    view.tv_header3.visibility = View.GONE
                }
            }
        })

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView which displays movies that current person performed in:
        inCastRecyclerView = rv_actorsInMovie.apply {
            this.layoutManager = inCastLayoutManager
            this.adapter = inCastAdapter
        }

        // RecyclerView which displays movies that current person was in crew of:
        inCrewRecyclerView = rv_crewInMovie.apply {
            this.layoutManager = inCrewLayoutManager
            this.adapter = inCrewAdapter
        }
    }
}