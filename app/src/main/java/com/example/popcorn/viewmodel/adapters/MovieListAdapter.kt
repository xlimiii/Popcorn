package com.example.popcorn.viewmodel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
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


class MovieListAdapter(private val movies : LiveData<List<Movie>>, private val movieVM : MovieViewModel, private val favVM : FavouriteViewModel) : RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {
    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListAdapter.MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_row, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieListAdapter.MovieHolder, position: Int) {
        val name = holder.itemView.findViewById<TextView>(R.id.tv_movieTitle)
        name.text = movies.value?.get(position)?.title.toString()

        val poster = holder.itemView.findViewById<ImageView>(R.id.iv_moviePoster)
        val url = "https://image.tmdb.org/t/p/w185${movies.value?.get(position)?.poster_path}"
        Glide.with(holder.itemView).load(url).centerCrop().into(poster)

        val movieRowBackground = holder.itemView.findViewById<ConstraintLayout>(R.id.movieRowBackground)
        movieRowBackground.setOnClickListener {  movies.value?.let { it1 -> movieVM.setCurrentMovie(it1.get(position).id) }
            movieRowBackground.findNavController().navigate(R.id.action_movieListFragment_to_movieDetailsFragment) }

        val addToFav = holder.itemView.findViewById<ImageButton>(R.id.btn_addMovieToFav)
        addToFav.setOnClickListener{ movies.value?.get(position)?.let { item -> favVM.addFavourite(item) } }
    }

    override fun getItemCount(): Int = movies.value?.size ?: 0
}