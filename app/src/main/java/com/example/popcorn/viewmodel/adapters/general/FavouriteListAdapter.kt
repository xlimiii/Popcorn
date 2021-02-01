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

class FavouriteListAdapter(private val favouriteMovies: LiveData<List<Favourite>>, private val favVM: FavouriteViewModel) : RecyclerView.Adapter<FavouriteListAdapter.MovieHolder>() {
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
        movieRowBackground.setOnClickListener { view->view.findNavController().navigate(R.id.action_favouriteListFragment_to_movieDetailsFragment) }

        val addToFav = holder.itemView.findViewById<ImageButton>(R.id.btn_addToFav)
        addToFav.visibility = View.GONE
        val delFromFav = holder.itemView.findViewById<ImageButton>(R.id.btn_delFromFav)
        delFromFav.visibility = View.VISIBLE
        delFromFav.setOnClickListener{ favouriteMovies.value?.get(position)?.let { item -> favVM.deleteFavorite(item.id) } }
    }

    override fun getItemCount() : Int = favouriteMovies.value?.size ?: 0
}