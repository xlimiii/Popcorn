package com.example.popcorn.viewmodel.adapters.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popcorn.R
import com.example.popcorn.model.GeneralObject
import com.example.popcorn.model.Person
import com.example.popcorn.viewmodel.MovieViewModel
import com.example.popcorn.viewmodel.PersonViewModel

class CrewInMovieAndTVShowAdapter(var peopleInMovie: LiveData<List<Person>>, val personVM: PersonViewModel) : RecyclerView.Adapter<CrewInMovieAndTVShowAdapter.PersonHolder>() {
    inner class PersonHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        return PersonHolder(view)
    }

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {

        val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
        val character = holder.itemView.findViewById<TextView>(R.id.tv_characterName)

        val poster = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
        val movieRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
        val url = "https://image.tmdb.org/t/p/w185${peopleInMovie.value?.get(position)?.profile_path}"
        Glide.with(holder.itemView)
                .load(url)
                .centerCrop()
                .into(poster)

        name.text = peopleInMovie.value?.get(position)?.name.toString()

        character.text = peopleInMovie.value?.get(position)?.department
        movieRowBackground.setOnClickListener {  peopleInMovie.value?.let { it1 -> personVM.setCurrentPerson(it1.get(position).id) }
            try{
            movieRowBackground.findNavController().navigate(R.id.action_movieDetailsFragment_to_actorDetailsFragment) }
        catch(e: Exception){
            movieRowBackground.findNavController().navigate(R.id.action_TVShowDetailsFragment_to_actorDetailsFragment)
        }
    }
    }

    override fun getItemCount(): Int {
        return peopleInMovie.value?.size?:0
    }
}