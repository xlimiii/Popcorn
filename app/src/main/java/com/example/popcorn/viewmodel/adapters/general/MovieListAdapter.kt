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

// Adapter used in MovieList and Home fragments, responsible for displaying list of popular movies and movies with matching title:
class MovieListAdapter(private val movies : LiveData<List<Movie>>,
                       private val movieViewModel : MovieViewModel,
                       private val favViewModel : FavouriteViewModel,
                       private val inFragment : String) : RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {

    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view: View =
            // Display in rows if in MovieListFragment (second tab):
            if (inFragment == "MovieListFragment")
                LayoutInflater.from(parent.context).inflate(R.layout.one_item_row, parent, false)
            // Display in tiles if in HomeFragment (first tab):
            else
                LayoutInflater.from(parent.context).inflate(R.layout.one_item_tile, parent, false)

        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        // Binding data in Movie List Fragment:
        if (inFragment == "MovieListFragment") {
            // Title:
            val name = holder.itemView.findViewById<TextView>(R.id.tv_titleOrName)
            name.text = movies.value?.get(position)?.title.toString()

            // Release date:
            val date = holder.itemView.findViewById<TextView>(R.id.tv_releaseOrBirth)
            if (!movies.value?.get(position)?.release_date.isNullOrEmpty())
                date.text = movies.value?.get(position)?.release_date.toString().slice(IntRange(0,3))

            // Poster:
            val poster = holder.itemView.findViewById<ImageView>(R.id.iv_posterOrPhoto)
            val url = "https://image.tmdb.org/t/p/w185${movies.value?.get(position)?.poster_path}"
            Glide.with(holder.itemView).load(url).centerCrop().placeholder(R.drawable.ic_outline_movie_24holder).into(poster)

            // Navigation between fragments - going to Movie Details:
            val movieRowBackground = holder.itemView.findViewById<ConstraintLayout>(R.id.rowBackground)
            movieRowBackground.setOnClickListener {
                movies.value?.let { item -> movieViewModel.setCurrentMovie(item[position].id) }
                movieRowBackground.findNavController().navigate(R.id.action_movieListFragment_to_movieDetailsFragment)
            }

            // Adding to favourites:
            val addToFav = holder.itemView.findViewById<ImageButton>(R.id.btn_addToFav)
            addToFav.setOnClickListener { movies.value?.get(position)?.let { item -> favViewModel.addFavourite(item) } }

            // Default visibility of "favourite buttons":
            val delFromFav = holder.itemView.findViewById<ImageButton>(R.id.btn_delFromFav)
            val favDate = holder.itemView.findViewById<TextView>(R.id.tv_favDate)
            addToFav.visibility = View.VISIBLE
            delFromFav.visibility = View.GONE
            favDate.text = ""

            // Deleting from favourites:
            val favouriteMovie = favViewModel.favourites.value?.find {
                x -> x.media_type == "movie" && x.movieOrTVShowID == movies.value?.get(position)?.id }
            if (favouriteMovie != null)
            {
                delFromFav.setOnClickListener {
                    favViewModel.deleteFavorite(favouriteMovie.id)
                    addToFav.visibility = View.VISIBLE
                    delFromFav.visibility = View.GONE
                }

                // Swapping visibility (deleting must be active, not adding):
                addToFav.visibility = View.GONE
                delFromFav.visibility = View.VISIBLE
                favDate.text = favouriteMovie.date
            }

        // Binding data in Home Fragment:
        } else {
            // Title:
            val name = holder.itemView.findViewById<TextView>(R.id.tv_titleOrName)
            name.text = movies.value?.get(position)?.title.toString()

            // Who was played:
            val character = holder.itemView.findViewById<TextView>(R.id.tv_characterOrDepartment)
            character.text = movies.value?.get(position)?.character

            // Poster:
            val poster = holder.itemView.findViewById<ImageView>(R.id.iv_posterOrPhoto)
            val url = "https://image.tmdb.org/t/p/w185${movies.value?.get(position)?.poster_path}"
            Glide.with(holder.itemView).load(url).centerCrop().placeholder(R.drawable.ic_outline_movie_24holder).into(poster)

            // Navigation between fragments - going to Movie Details:
            val movieRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
            movieRowBackground.setOnClickListener {
                movies.value?.let { item -> movieViewModel.setCurrentMovie(item[position].id) }
                movieRowBackground.findNavController().navigate(R.id.action_homeFragment_to_movieDetailsFragment)
            }
        }
    }

    override fun getItemCount(): Int = movies.value?.size ?: 0
}