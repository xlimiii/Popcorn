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

// Adapter used in FavouriteList fragment, responsible for displaying list of favourite movies and TV shows:
class FavouriteListAdapter(private val favouriteMoviesAndTVShows: LiveData<List<Favourite>>,
                           private val favViewModel: FavouriteViewModel,
                           private val movieViewModel: MovieViewModel,
                           private val tvShowViewModel: TVShowViewModel) : RecyclerView.Adapter<FavouriteListAdapter.FavouriteHolder>() {

    inner class FavouriteHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_item_row, parent, false)
        return FavouriteHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteHolder, position: Int) {
        // Title:
        val name = holder.itemView.findViewById<TextView>(R.id.tv_titleOrName)
        name.text = favouriteMoviesAndTVShows.value?.get(position)?.title.toString()

        // Release date:
        val date = holder.itemView.findViewById<TextView>(R.id.tv_releaseOrBirth)
        if (!favouriteMoviesAndTVShows.value?.get(position)?.release_date.isNullOrEmpty())
            date.text = favouriteMoviesAndTVShows.value?.get(position)?.release_date.toString().slice(IntRange(0,3))

        // Poster:
        val poster = holder.itemView.findViewById<ImageView>(R.id.iv_posterOrPhoto)
        val url = "https://image.tmdb.org/t/p/w185${favouriteMoviesAndTVShows.value?.get(position)?.poster_path}"
        if(favouriteMoviesAndTVShows.value?.get(position)?.media_type == "movie")
            Glide.with(holder.itemView).load(url).centerCrop().placeholder(R.drawable.ic_outline_movie_24holder).into(poster)
        else
            Glide.with(holder.itemView).load(url).centerCrop().placeholder(R.drawable.ic_twotone_live_tv_24holder).into(poster)

        // Navigation between fragments:
        val movieRowBackground = holder.itemView.findViewById<ConstraintLayout>(R.id.rowBackground)
        movieRowBackground.setOnClickListener {
            view ->
            // Going to Movie Details:
            if (favouriteMoviesAndTVShows.value?.get(position)?.media_type == "movie")
                view.findNavController().navigate(R.id.action_favouriteListFragment_to_movieDetailsFragment)
            // Going to TV Show Details:
            else
                view.findNavController().navigate(R.id.action_favouriteListFragment_to_TVShowDetailsFragment)

            // Updating current item for displaying in details:
            if (favouriteMoviesAndTVShows.value?.get(position)?.media_type == "movie") movieViewModel.setCurrentMovie(favouriteMoviesAndTVShows.value!![position].movieOrTVShowID)
            else tvShowViewModel.setCurrentTVShow(favouriteMoviesAndTVShows.value!![position].movieOrTVShowID)
        }

        // Displaying button responsible for deleting item from favourites:
        val addToFav = holder.itemView.findViewById<ImageButton>(R.id.btn_addToFav)
        val delFromFav = holder.itemView.findViewById<ImageButton>(R.id.btn_delFromFav)
        val favDate = holder.itemView.findViewById<TextView>(R.id.tv_favDate)
        favDate.text = favouriteMoviesAndTVShows.value?.get(position)?.date
        addToFav.visibility = View.GONE
        delFromFav.visibility = View.VISIBLE
        delFromFav.setOnClickListener{ favouriteMoviesAndTVShows.value?.get(position)?.let { item -> favViewModel.deleteFavorite(item.id) } }
    }

    override fun getItemCount() : Int = favouriteMoviesAndTVShows.value?.size ?: 0
}