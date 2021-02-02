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

class CastInMovieAndTVShowAdapter(private val cast: LiveData<List<Person>>,
                                  private val personVM: PersonViewModel) : RecyclerView.Adapter<CastInMovieAndTVShowAdapter.MovieHolder>() {

    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
        name.text = cast.value?.get(position)?.name.toString()

        val character = holder.itemView.findViewById<TextView>(R.id.tv_characterName)
        character.text = cast.value?.get(position)?.character

        val avatar = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
        val url = "https://image.tmdb.org/t/p/w185${cast.value?.get(position)?.profile_path}"
        Glide.with(holder.itemView).load(url).centerCrop().into(avatar)

        val rowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
        rowBackground.setOnClickListener {
            cast.value?.let { item -> personVM.setCurrentPerson(item[position].id) }
            try { rowBackground.findNavController().navigate(R.id.action_movieDetailsFragment_to_personDetailsFragment) }
            catch(e: Exception) { rowBackground.findNavController().navigate(R.id.action_TVShowDetailsFragment_to_personDetailsFragment) }
        }
    }

    override fun getItemCount(): Int = cast.value?.size ?: 0
}