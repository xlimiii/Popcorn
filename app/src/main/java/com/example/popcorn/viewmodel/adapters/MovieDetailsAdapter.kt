package com.example.popcorn.viewmodel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popcorn.R
import com.example.popcorn.model.Movie
import com.example.popcorn.model.Person
import com.example.popcorn.viewmodel.MovieViewModel


class MovieDetailsAdapter(var actorsInMovies: LiveData<List<Person>>) : RecyclerView.Adapter<MovieDetailsAdapter.MovieHolder>() {
    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailsAdapter.MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_row, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieDetailsAdapter.MovieHolder, position: Int) {

        val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
        val avatar = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
        val actorRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.actorRowBackground)
        val url = "https://image.tmdb.org/t/p/w185${actorsInMovies.value?.get(position)?.profile_path}"
        Glide.with(holder.itemView)
            .load(url)
            .centerCrop()
            .into(avatar)
        name.text = actorsInMovies.value?.get(position)?.name.toString()
    }

    override fun getItemCount(): Int {
        return 1
    }
}