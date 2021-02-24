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
import com.example.popcorn.viewmodel.MovieViewModel
import com.example.popcorn.viewmodel.TVShowViewModel

// Adapter used in PersonDetails fragment, responsible for displaying movies and TV shows that current person performed in or was in crew of:
class MoviesAndTVShowsInPersonAdapter(private val moviesAndTVShows: LiveData<List<GeneralObject>>,
                                      private val movieViewModel: MovieViewModel,
                                      private val tvShowViewModel: TVShowViewModel,
                                      private val inCastOrInCrew : String) : RecyclerView.Adapter<MoviesAndTVShowsInPersonAdapter.PersonHolder>() {

    inner class PersonHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_item_tile, parent, false)
        return PersonHolder(view)
    }

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        // Title:
        val name = holder.itemView.findViewById<TextView>(R.id.tv_titleOrName)
        name.text =
                if (moviesAndTVShows.value?.get(position)?.media_type == "movie") moviesAndTVShows.value?.get(position)?.title.toString()
                else moviesAndTVShows.value?.get(position)?.name.toString()

        // Who was played / for what was responsible:
        val character = holder.itemView.findViewById<TextView>(R.id.tv_characterOrDepartment)
        character.text =
                if (inCastOrInCrew == "inCast") moviesAndTVShows.value?.get(position)?.character
                else moviesAndTVShows.value?.get(position)?.department

        // Poster:
        val poster = holder.itemView.findViewById<ImageView>(R.id.iv_posterOrPhoto)
        val url = "https://image.tmdb.org/t/p/w185${moviesAndTVShows.value?.get(position)?.poster_path}"
        if (moviesAndTVShows.value?.get(position)?.media_type=="movie")
            Glide.with(holder.itemView).load(url).centerCrop().placeholder(R.drawable.ic_outline_movie_24holder).into(poster)
        else
            Glide.with(holder.itemView).load(url).centerCrop().placeholder(R.drawable.ic_twotone_live_tv_24holder).into(poster)

        // Navigation between fragments:
        val rowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
        rowBackground.setOnClickListener {
            // Going to Movie Details:
            if (moviesAndTVShows.value?.get(position)?.media_type == "movie")
            {
                moviesAndTVShows.value?.let { item -> movieViewModel.setCurrentMovie(item[position].id) }
                rowBackground.findNavController().navigate(R.id.action_personDetailsFragment_to_movieDetailsFragment)
            }
            // Going to TV Show Details:
            else
            {
                moviesAndTVShows.value?.let { item -> tvShowViewModel.setCurrentTVShow(item[position].id) }
                rowBackground.findNavController().navigate(R.id.action_personDetailsFragment_to_TVShowDetailsFragment)
            }
        }
    }

    override fun getItemCount(): Int = moviesAndTVShows.value?.size ?: 0
}