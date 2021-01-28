package com.example.popcorn.viewmodel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.model.Movie
import com.example.popcorn.viewmodel.MovieViewModel
import com.bumptech.glide.Glide
import com.example.popcorn.viewmodel.FavouriteViewModel


class MovieListAdapter(val movies: LiveData<List<Movie>>, val movieVM: MovieViewModel) : RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {
    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListAdapter.MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_row, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieListAdapter.MovieHolder, position: Int) {
        val name = holder.itemView.findViewById<TextView>(R.id.tv_movieTitle)
        val poster = holder.itemView.findViewById<ImageView>(R.id.iv_moviePoster)
        val movieRowBackground = holder.itemView.findViewById<ConstraintLayout>(R.id.movieRowBackground)
        val addToFav = holder.itemView.findViewById<ImageButton>(R.id.btn_addMovieToFav)
        val url = "https://image.tmdb.org/t/p/w185${movies.value?.get(position)?.poster_path}"
        Glide.with(holder.itemView)
                .load(url)
                .centerCrop()
                .into(poster)
        name.text = movies.value?.get(position)?.title.toString()
        movieRowBackground.setOnClickListener {
                view->view.findNavController().navigate(R.id.action_movieListFragment_to_movieDetailsFragment)
        }
        addToFav.setOnClickListener{
            movies.value?.get(position)?.let { it1 -> movieVM.addFavourite(it1.id) }
        }
    }

    override fun getItemCount(): Int {
        return movies.value?.size?:0
    }
}