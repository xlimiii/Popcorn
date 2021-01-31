package com.example.popcorn.viewmodel.adapters

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
import com.example.popcorn.model.Movie
import com.example.popcorn.viewmodel.MovieViewModel

class PersonDetailsAdapter(var moviesInPerson: LiveData<List<Movie>>, val movieVM: MovieViewModel) : RecyclerView.Adapter<PersonDetailsAdapter.PersonHolder>() {
    inner class PersonHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonDetailsAdapter.PersonHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        return PersonHolder(view)
    }

    override fun onBindViewHolder(holder: PersonDetailsAdapter.PersonHolder, position: Int) {

        val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
        val character = holder.itemView.findViewById<TextView>(R.id.tv_characterName)

        val poster = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
        val movieRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
        val url = "https://image.tmdb.org/t/p/w185${moviesInPerson.value?.get(position)?.poster_path}"
        Glide.with(holder.itemView)
            .load(url)
            .centerCrop()
            .into(poster)
        name.text = moviesInPerson.value?.get(position)?.title.toString()
        character.text = moviesInPerson.value?.get(position)?.character
        movieRowBackground.setOnClickListener {  moviesInPerson.value?.let { it1 -> movieVM.setCurrentMovie(it1.get(position).id) }
            movieRowBackground.findNavController().navigate(R.id.action_actorDetailsFragment_to_movieDetailsFragment) }
    }

    override fun getItemCount(): Int {
        return moviesInPerson.value?.size?:0
    }
}