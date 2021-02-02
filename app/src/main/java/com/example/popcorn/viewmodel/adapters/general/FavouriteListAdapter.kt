package com.example.popcorn.viewmodel.adapters.general

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
import com.bumptech.glide.Glide
import com.example.popcorn.R
import com.example.popcorn.model.db.Favourite
import com.example.popcorn.viewmodel.FavouriteViewModel
import com.example.popcorn.viewmodel.MovieViewModel
import com.example.popcorn.viewmodel.TVShowViewModel

class FavouriteListAdapter(private val favouriteMovies: LiveData<List<Favourite>>,
                           private val favVM: FavouriteViewModel,
                           private val movieVM: MovieViewModel,
                           private val tvsVM: TVShowViewModel) : RecyclerView.Adapter<FavouriteListAdapter.MovieHolder>() {

    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_row, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val name = holder.itemView.findViewById<TextView>(R.id.tv_movieTitle)
        name.text = favouriteMovies.value?.get(position)?.title.toString()

        val poster = holder.itemView.findViewById<ImageView>(R.id.iv_moviePoster)
        val url = "https://image.tmdb.org/t/p/w185${favouriteMovies.value?.get(position)?.poster_path}"
        Glide.with(holder.itemView).load(url).centerCrop().into(poster)

        val movieRowBackground = holder.itemView.findViewById<ConstraintLayout>(R.id.movieRowBackground)
        movieRowBackground.setOnClickListener {
                view -> if (favouriteMovies.value?.get(position)?.media_type == "movie") view.findNavController().navigate(R.id.action_favouriteListFragment_to_movieDetailsFragment)
                        else view.findNavController().navigate(R.id.action_favouriteListFragment_to_TVShowDetailsFragment)

                if (favouriteMovies.value?.get(position)?.media_type == "movie") movieVM.setCurrentMovie(favouriteMovies.value!![position].movieOrTVShowID)
                else tvsVM.setCurrentTVShow(favouriteMovies.value!![position].movieOrTVShowID)
        }

        val addToFav = holder.itemView.findViewById<ImageButton>(R.id.btn_addToFav)
        val delFromFav = holder.itemView.findViewById<ImageButton>(R.id.btn_delFromFav)
        addToFav.visibility = View.GONE
        delFromFav.visibility = View.VISIBLE
        delFromFav.setOnClickListener{ favouriteMovies.value?.get(position)?.let { item -> favVM.deleteFavorite(item.id) } }
    }

    override fun getItemCount() : Int = favouriteMovies.value?.size ?: 0
}