package com.example.popcorn.viewmodel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popcorn.R
import com.example.popcorn.model.Person
import com.example.popcorn.model.db.Favourite
import com.example.popcorn.viewmodel.FavouriteViewModel

class FavouriteListAdapter(val favourites: LiveData<List<Favourite>>, val favVM: FavouriteViewModel) : RecyclerView.Adapter<FavouriteListAdapter.MovieHolder>() {
    inner class MovieHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteListAdapter.MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_temp_row, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteListAdapter.MovieHolder, position: Int) {
        val idFavMovie = holder.itemView.findViewById<TextView>(R.id.tv_idFavMovie)

            idFavMovie.text = favourites.value?.get(position)?.movieID.toString()
    }

    override fun getItemCount(): Int {
        return favourites.value?.size?:0
    }
}