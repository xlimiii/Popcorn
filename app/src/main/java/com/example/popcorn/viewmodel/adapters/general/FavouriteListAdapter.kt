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
                           private val tvsVM: TVShowViewModel) : RecyclerView.Adapter<FavouriteListAdapter.FavouriteHolder>() {

    inner class FavouriteHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_tv_fav_row, parent, false)
        return FavouriteHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteHolder, position: Int) {
        // title:
        val name = holder.itemView.findViewById<TextView>(R.id.tv_movieTitle)
        name.text = favouriteMovies.value?.get(position)?.title.toString()

        // poster:
        val poster = holder.itemView.findViewById<ImageView>(R.id.iv_moviePoster)
        if (!favouriteMovies.value?.get(position)?.poster_path.isNullOrEmpty())
        {
            val url = "https://image.tmdb.org/t/p/w185${favouriteMovies.value?.get(position)?.poster_path}"
            Glide.with(holder.itemView).load(url).centerCrop().into(poster)
        }
        //else Glide.with(holder.itemView).load("LINK HERE").centerCrop().into(poster)

        // navigation:
        val movieRowBackground = holder.itemView.findViewById<ConstraintLayout>(R.id.movieRowBackground)
        movieRowBackground.setOnClickListener {
                view -> if (favouriteMovies.value?.get(position)?.media_type == "movie") view.findNavController().navigate(R.id.action_favouriteListFragment_to_movieDetailsFragment)
                        else view.findNavController().navigate(R.id.action_favouriteListFragment_to_TVShowDetailsFragment)

                if (favouriteMovies.value?.get(position)?.media_type == "movie") movieVM.setCurrentMovie(favouriteMovies.value!![position].movieOrTVShowID)
                else tvsVM.setCurrentTVShow(favouriteMovies.value!![position].movieOrTVShowID)
        }

        // fav button:
        val addToFav = holder.itemView.findViewById<ImageButton>(R.id.btn_addToFav)
        val delFromFav = holder.itemView.findViewById<ImageButton>(R.id.btn_delFromFav)
        val favDate = holder.itemView.findViewById<TextView>(R.id.tv_favDate)
        favDate.text = favouriteMovies.value?.get(position)?.date
        addToFav.visibility = View.GONE
        delFromFav.visibility = View.VISIBLE
        delFromFav.setOnClickListener{ favouriteMovies.value?.get(position)?.let { item -> favVM.deleteFavorite(item.id) } }
    }

    override fun getItemCount() : Int = favouriteMovies.value?.size ?: 0
}