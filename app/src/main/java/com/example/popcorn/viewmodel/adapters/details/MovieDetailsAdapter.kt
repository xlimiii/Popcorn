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
import com.example.popcorn.model.Person
import com.example.popcorn.viewmodel.PersonViewModel


class MovieDetailsAdapter(var actorsInMovies: LiveData<List<Person>>, val personVM: PersonViewModel) : RecyclerView.Adapter<MovieDetailsAdapter.MovieHolder>() {
    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {

        val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
        val avatar = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
        val character = holder.itemView.findViewById<TextView>(R.id.tv_characterName)
        val actorRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
        val url = "https://image.tmdb.org/t/p/w185${actorsInMovies.value?.get(position)?.profile_path}"
        Glide.with(holder.itemView)
            .load(url)
            .centerCrop()
            .into(avatar)
        name.text = actorsInMovies.value?.get(position)?.name.toString()
        character.text = actorsInMovies.value?.get(position)?.character
        actorRowBackground.setOnClickListener {  actorsInMovies.value?.let { it1 -> personVM.setCurrentPerson(it1.get(position).id) }
            actorRowBackground.findNavController().navigate(R.id.action_movieDetailsFragment_to_actorDetailsFragment) }
    }

    override fun getItemCount(): Int {
        return actorsInMovies.value?.size?:0
    }
}