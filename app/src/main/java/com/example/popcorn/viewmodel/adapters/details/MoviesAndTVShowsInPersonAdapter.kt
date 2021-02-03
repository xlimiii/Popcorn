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

class MoviesAndTVShowsInPersonAdapter(private val moviesAndTVShows: LiveData<List<GeneralObject>>,
                                      private val movieVM: MovieViewModel,
                                      private val tvsVM: TVShowViewModel,
                                      private val inCastOrInCrew : String) : RecyclerView.Adapter<MoviesAndTVShowsInPersonAdapter.PersonHolder>() {

    inner class PersonHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        return PersonHolder(view)
    }

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        // title:
        val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
        name.text =
                if (moviesAndTVShows.value?.get(position)?.media_type == "movie") moviesAndTVShows.value?.get(position)?.title.toString()
                else moviesAndTVShows.value?.get(position)?.name.toString()

        // who was played:
        val character = holder.itemView.findViewById<TextView>(R.id.tv_characterName)
        character.text =
                if (inCastOrInCrew == "inCast") moviesAndTVShows.value?.get(position)?.character
                else moviesAndTVShows.value?.get(position)?.department

        // poster:
        val poster = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
        if (!moviesAndTVShows.value?.get(position)?.poster_path.isNullOrEmpty())
        {
            val url = "https://image.tmdb.org/t/p/w185${moviesAndTVShows.value?.get(position)?.poster_path}"
            Glide.with(holder.itemView).load(url).centerCrop().into(poster)
        }
        //else Glide.with(holder.itemView).load("LINK HERE").centerCrop().into(poster)

        // navigation:
        val rowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
        rowBackground.setOnClickListener {
            if (moviesAndTVShows.value?.get(position)?.media_type == "movie")
            {
                moviesAndTVShows.value?.let { item -> movieVM.setCurrentMovie(item[position].id) }
                rowBackground.findNavController().navigate(R.id.action_personDetailsFragment_to_movieDetailsFragment)
            }
            else
            {
                moviesAndTVShows.value?.let { item -> tvsVM.setCurrentTVShow(item[position].id) }
                rowBackground.findNavController().navigate(R.id.action_personDetailsFragment_to_TVShowDetailsFragment)
            }
        }
    }

    override fun getItemCount(): Int = moviesAndTVShows.value?.size ?: 0
}