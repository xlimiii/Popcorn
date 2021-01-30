package com.example.popcorn.viewmodel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.model.Movie
import com.example.popcorn.viewmodel.MovieViewModel


class MovieDetailsAdapter(val currentMovie: LiveData<Movie>, val changeCurrentMovie: (s: Movie) -> Unit) : RecyclerView.Adapter<MovieDetailsAdapter.MovieHolder>() {
    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailsAdapter.MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_movie_details, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieDetailsAdapter.MovieHolder, position: Int) {
        val movieTitle = holder.itemView.findViewById<TextView>(R.id.tv_movieDetailsTitle)

        // movieTitle.text = currentMovie.value?.title
    }

    override fun getItemCount(): Int {
        return 1
    }
}