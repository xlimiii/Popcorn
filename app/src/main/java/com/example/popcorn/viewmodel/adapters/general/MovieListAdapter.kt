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
import kotlinx.android.synthetic.main.fragment_details.view.*
import org.w3c.dom.Text


class MovieListAdapter(private val movies : LiveData<List<Movie>>,
                       private val movieVM : MovieViewModel,
                       private val favVM : FavouriteViewModel,
                       private val inFragment : String) : RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {

    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view: View =
                if (inFragment == "MovieListFragment")
                    LayoutInflater.from(parent.context).inflate(R.layout.movie_tv_fav_row, parent, false)
                else
                    LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        if (inFragment == "MovieListFragment") {
            // title:
            val name = holder.itemView.findViewById<TextView>(R.id.tv_movieTitle)
            name.text = movies.value?.get(position)?.title.toString()

            // poster:
            val poster = holder.itemView.findViewById<ImageView>(R.id.iv_moviePoster)
            val url = "https://image.tmdb.org/t/p/w185${movies.value?.get(position)?.poster_path}"
            Glide.with(holder.itemView).load(url).centerCrop().placeholder(R.drawable.ic_outline_movie_24holder).into(poster)

            // navigation:
            val movieRowBackground = holder.itemView.findViewById<ConstraintLayout>(R.id.movieRowBackground)
            movieRowBackground.setOnClickListener {
                movies.value?.let { item -> movieVM.setCurrentMovie(item[position].id) }
                movieRowBackground.findNavController().navigate(R.id.action_movieListFragment_to_movieDetailsFragment)
            }

            // fav button:
            val addToFav = holder.itemView.findViewById<ImageButton>(R.id.btn_addToFav)
            val delFromFav = holder.itemView.findViewById<ImageButton>(R.id.btn_delFromFav)
            val favDate = holder.itemView.findViewById<TextView>(R.id.tv_favDate)
            addToFav.setOnClickListener { movies.value?.get(position)?.let { item -> favVM.addFavourite(item) } }
            addToFav.visibility = View.VISIBLE
            delFromFav.visibility = View.GONE
            favDate.text = ""

            // IF THIS MOVIE IS IN FAVOURITES:
            val favouriteMovie = favVM.favourites.value?.find {
                x -> x.media_type == "movie" && x.movieOrTVShowID == movies.value?.get(position)?.id }
            if (favouriteMovie != null)
            {
                delFromFav.setOnClickListener {
                    favVM.deleteFavorite(favouriteMovie.id)
                    addToFav.visibility = View.VISIBLE
                    delFromFav.visibility = View.GONE
                }
                addToFav.visibility = View.GONE
                delFromFav.visibility = View.VISIBLE
                favDate.text = favouriteMovie.date
            }

        } else {
            // title:
            val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
            name.text = movies.value?.get(position)?.title.toString()

            // who was played:
            val character = holder.itemView.findViewById<TextView>(R.id.tv_characterName)
            character.text = movies.value?.get(position)?.character

            // poster:
            val poster = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
            val url = "https://image.tmdb.org/t/p/w185${movies.value?.get(position)?.poster_path}"
            Glide.with(holder.itemView).load(url).centerCrop().placeholder(R.drawable.ic_outline_movie_24holder).into(poster)

            // navigation:
            val movieRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
            movieRowBackground.setOnClickListener {
                movies.value?.let { item -> movieVM.setCurrentMovie(item[position].id) }
                movieRowBackground.findNavController().navigate(R.id.action_homeFragment_to_movieDetailsFragment)
            }
        }

    }

    override fun getItemCount(): Int = movies.value?.size ?: 0
}