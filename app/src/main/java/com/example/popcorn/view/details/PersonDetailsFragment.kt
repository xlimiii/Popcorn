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
        view.tv_header1.text = resources.getString(R.string.biographyHeader)
        view.tv_header2.text = resources.getString(R.string.inCastHeader)
        view.tv_header3.text = resources.getString(R.string.inCrewHeader)

        personViewModel.currentPerson.observe(viewLifecycleOwner, {
            // name:
            view.tv_movieDetailsTitle.text = it.name

            // biography:
            if (it.biography.isNullOrEmpty())
            {
                view.tv_header1.visibility = View.GONE
                view.tv_movieDescription.visibility = View.GONE
            }
            else
            {
                view.tv_header1.visibility = View.VISIBLE
                view.tv_movieDescription.text = it.biography
                view.tv_movieDescription.visibility = View.VISIBLE
            }

            // date of birth:
            if (it.birthday.isNullOrEmpty())
            {
                view.tv_year.visibility = View.GONE
            }
            else
            {
                view.tv_year.text = "Born: ${it.birthday}"
                view.tv_year.visibility = View.VISIBLE
            }

            // date of death:
            if (it.deathday.isNullOrEmpty())
            {
                view.tv_year2.visibility = View.GONE
            }
            else
            {
                view.tv_year2.text = "Died: ${it.deathday}"
                view.tv_year2.visibility = View.VISIBLE
            }

            // place of birth:
            view.tv_oryginalLang.text = "Place of Birth: " + it.place_of_birth
            if (it.place_of_birth.isNullOrEmpty()) view.tv_oryginalLang.visibility = View.GONE
            else view.tv_oryginalLang.visibility = View.VISIBLE

            // department:
            view.tv_genresForMovie.text = "Known for: " + it.known_for_department
            if (it.known_for_department.isNullOrEmpty()) view.tv_genresForMovie.visibility = View.GONE
            else view.tv_genresForMovie.visibility = View.VISIBLE

            // photo:
            val url = "https://image.tmdb.org/t/p/w185${it.profile_path}"
            val placeholderImg : Int = when(it.gender) {
                2 -> R.drawable.ic_baseline_person_outline_24
                1 -> R.drawable.ic_baseline_person_outline_242
                else -> R.drawable.ic_baseline_person_outline_24e
            }
            Glide.with(view.iv_movieDetailsPoster).load(url).centerCrop().placeholder(placeholderImg).into(view.iv_movieDetailsPoster)
            personViewModel.setCurrentPersonCollection(it.id)
        })

        // performed in
        personViewModel.currentPersonInCastCollection.observe(viewLifecycleOwner, {
            inCastAdapter.notifyDataSetChanged()
            if (personViewModel.currentPersonInCastCollection.value.isNullOrEmpty())
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

        // crew of
        personViewModel.currentPersonInCrewCollection.observe(viewLifecycleOwner, {
            inCrewAdapter.notifyDataSetChanged()
            if (personViewModel.currentPersonInCrewCollection.value.isNullOrEmpty())
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