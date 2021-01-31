package com.example.popcorn.view

import android.graphics.text.LineBreaker
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popcorn.R
import com.example.popcorn.viewmodel.PersonViewModel
import com.example.popcorn.viewmodel.adapters.PersonDetailsAdapter
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*

class PersonDetailsFragment : Fragment() {
    private lateinit var personViewModel: PersonViewModel
    private lateinit var personDetailsAdapter: PersonDetailsAdapter
    private lateinit var myLayoutManager : LinearLayoutManager
    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        myLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        personViewModel = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)
        personViewModel.currentPerson.value?.id?.let {
            personViewModel.setMoviesConnectedWithCurrentPerson(
                it
            )
        }
        val view =  inflater.inflate(R.layout.fragment_details, container, false)
        view.tv_movieDescription.setJustificationMode((LineBreaker.JUSTIFICATION_MODE_INTER_WORD))
        view.tv_header1.text = "Biography"
        view.tv_header2.text = "Movies"

        personDetailsAdapter= PersonDetailsAdapter(personViewModel.moviesConnectedWithCurrentPerson)
        personViewModel.currentPerson.observe(viewLifecycleOwner, { it ->
            view.tv_movieDetailsTitle.text = it.name
            view.tv_movieDescription.text = it.biography
            view.tv_year.text = it.birthday
            if(it.deathday !=null) {
                view.tv_year2.text = it.deathday
            }
            view.tv_genresForMovie.setText( "Known for: " +it.known_for_department)
            if(it.place_of_birth !=null)
                view.tv_oryginalLang.setText( "Place of Birth: " +it.place_of_birth)
            val url = "https://image.tmdb.org/t/p/w185${it.profile_path}"
            Glide.with(view.iv_movieDetailsPoster)
                .load(url)
                .centerCrop()
                .into(view.iv_movieDetailsPoster)
            personViewModel.setMoviesConnectedWithCurrentPerson(it.id)
        })
        personViewModel.moviesConnectedWithCurrentPerson!!.observe(viewLifecycleOwner, {personDetailsAdapter.notifyDataSetChanged()})


        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = rv_actorsInMovie.apply {
            this.layoutManager = myLayoutManager
            this.adapter = personDetailsAdapter
        }





    }
    companion object { fun newInstance() = MovieDetailsFragment() }
}