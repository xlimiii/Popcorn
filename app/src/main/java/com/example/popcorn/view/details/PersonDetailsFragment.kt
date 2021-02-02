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
import com.example.popcorn.viewmodel.adapters.details.CrewAdapter
import com.example.popcorn.viewmodel.adapters.details.PersonDetailsAdapter
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*

class PersonDetailsFragment : Fragment() {
    private lateinit var personViewModel: PersonViewModel
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var personDetailsAdapter: PersonDetailsAdapter
    private lateinit var crewAdapter: CrewAdapter

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

        personViewModel = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)
        movieViewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        personViewModel.currentPerson.value?.id?.let { personViewModel.setCurrentPersonCollection(it) }
        val view =  inflater.inflate(R.layout.fragment_details, container, false)
        view.tv_movieDescription.justificationMode = (LineBreaker.JUSTIFICATION_MODE_INTER_WORD)
        view.tv_header1.text = "Biography"
        view.tv_header2.text = "Performed in"
        view.tv_header3.text = "Crew of"

        personDetailsAdapter= PersonDetailsAdapter(personViewModel.currentPersonInCastCollection, movieViewModel)
        crewAdapter= CrewAdapter(personViewModel.currentPersonInCrewCollection, movieViewModel)
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

        personViewModel.currentPersonInCastCollection.observe(viewLifecycleOwner, { personDetailsAdapter.notifyDataSetChanged() })
        personViewModel.currentPersonInCrewCollection.observe(viewLifecycleOwner, { crewAdapter.notifyDataSetChanged() })

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = rv_actorsInMovie.apply {
            this.layoutManager = myLayoutManager
            this.adapter = personDetailsAdapter
        }
        recyclerView2 = rv_crewInMovie.apply {
            this.layoutManager = myLayoutManager2
            this.adapter = crewAdapter
        }
    }

    companion object { fun newInstance() = MovieDetailsFragment() }
}