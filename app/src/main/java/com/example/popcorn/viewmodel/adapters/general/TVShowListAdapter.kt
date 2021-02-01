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
import com.example.popcorn.model.TVShow
import com.example.popcorn.viewmodel.FavouriteViewModel
import com.example.popcorn.viewmodel.TVShowViewModel

class TVShowListAdapter(private val TVShows : LiveData<List<TVShow>>,
                        private val TVShowVM : TVShowViewModel,
                        private val favVM : FavouriteViewModel) : RecyclerView.Adapter<TVShowListAdapter.TVShowHolder>() {

    inner class TVShowHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_row, parent, false)
        return TVShowHolder(view)
    }

    override fun onBindViewHolder(holder: TVShowHolder, position: Int) {
        val name = holder.itemView.findViewById<TextView>(R.id.tv_movieTitle)
        name.text = TVShows.value?.get(position)?.name.toString()

        val poster = holder.itemView.findViewById<ImageView>(R.id.iv_moviePoster)
        val url = "https://image.tmdb.org/t/p/w185${TVShows.value?.get(position)?.poster_path}"
        Glide.with(holder.itemView).load(url).centerCrop().into(poster)

        val movieRowBackground = holder.itemView.findViewById<ConstraintLayout>(R.id.movieRowBackground)
        movieRowBackground.setOnClickListener {
            TVShows.value?.get(position)?.let { item -> TVShowVM.setCurrentTVShow(item.id) }
            movieRowBackground.findNavController().navigate(R.id.action_tvShowListFragment_to_TVShowDetailsFragment)
        }

        val addToFav = holder.itemView.findViewById<ImageButton>(R.id.btn_addToFav)
        addToFav.setOnClickListener{ TVShows.value?.get(position)?.let { item -> favVM.addFavourite(item) } }

        // IF THIS MOVIE IS IN FAVOURITES:
        val favouriteMovie = favVM.favourites.value?.find {
            x -> x.media_type == "tv" && x.movieOrTVShowID == TVShows.value?.get(position)?.id }
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
    }

    override fun getItemCount(): Int = TVShows.value?.size ?: 0
}