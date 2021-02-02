package com.example.popcorn.viewmodel.adapters.general

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


class MovieListAdapter(private val movies : LiveData<List<Movie>>,
                       private val movieVM : MovieViewModel,
                       private val favVM : FavouriteViewModel,
                       private val fromCalled: Int) : RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {

    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view: View =
                if (fromCalled == 0) LayoutInflater.from(parent.context).inflate(R.layout.movie_row, parent, false)
                else LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val url = "https://image.tmdb.org/t/p/w185${movies.value?.get(position)?.poster_path}"

        if (fromCalled == 0) {
            val name = holder.itemView.findViewById<TextView>(R.id.tv_movieTitle)
            name.text = movies.value?.get(position)?.title.toString()

            val poster = holder.itemView.findViewById<ImageView>(R.id.iv_moviePoster)
            Glide.with(holder.itemView).load(url).centerCrop().into(poster)

            val movieRowBackground = holder.itemView.findViewById<ConstraintLayout>(R.id.movieRowBackground)
            movieRowBackground.setOnClickListener {
                movies.value?.let { item -> movieVM.setCurrentMovie(item[position].id) }
                movieRowBackground.findNavController().navigate(R.id.action_movieListFragment_to_movieDetailsFragment)
            }

            val addToFav = holder.itemView.findViewById<ImageButton>(R.id.btn_addToFav)
            addToFav.setOnClickListener { movies.value?.get(position)?.let { item -> favVM.addFavourite(item) } }

            // IF THIS MOVIE IS IN FAVOURITES:
            val favouriteMovie = favVM.favourites.value?.find {
                x -> x.media_type == "movie" && x.movieOrTVShowID == movies.value?.get(position)?.id }
            if (favouriteMovie != null)
            {
                val delFromFav = holder.itemView.findViewById<ImageButton>(R.id.btn_delFromFav)
                delFromFav.setOnClickListener {
                    favVM.deleteFavorite(favouriteMovie.id)
                    addToFav.visibility = View.VISIBLE
                    delFromFav.visibility = View.GONE
                }
                addToFav.visibility = View.GONE
                delFromFav.visibility = View.VISIBLE
            }

        } else {
            val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
            name.text = movies.value?.get(position)?.title.toString()

            val character = holder.itemView.findViewById<TextView>(R.id.tv_characterName)
            character.text = movies.value?.get(position)?.character

            val poster = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
            Glide.with(holder.itemView).load(url).centerCrop().into(poster)

            val movieRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
            movieRowBackground.setOnClickListener {
                movies.value?.let { item -> movieVM.setCurrentMovie(item[position].id) }
                movieRowBackground.findNavController().navigate(R.id.action_homeFragment_to_movieDetailsFragment)
            }
        }

    }

    override fun getItemCount(): Int = movies.value?.size ?: 0
}