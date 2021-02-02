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

class PersonInCastAdapter(private val moviesAndTVShows: LiveData<List<GeneralObject>>,
                          private val movieVM: MovieViewModel,
                          private val tvsVM: TVShowViewModel) : RecyclerView.Adapter<PersonInCastAdapter.PersonHolder>() {

    inner class PersonHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        return PersonHolder(view)
    }

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {

        val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
        name.text =
                if (moviesAndTVShows.value?.get(position)?.media_type == "movie") moviesAndTVShows.value?.get(position)?.title.toString()
                else moviesAndTVShows.value?.get(position)?.name.toString()

        val character = holder.itemView.findViewById<TextView>(R.id.tv_characterName)
        character.text = moviesAndTVShows.value?.get(position)?.character

        val poster = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
        val url = "https://image.tmdb.org/t/p/w185${moviesAndTVShows.value?.get(position)?.poster_path}"
        Glide.with(holder.itemView).load(url).centerCrop().into(poster)

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