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
import com.bumptech.glide.Glide
import com.example.popcorn.R
import com.example.popcorn.model.TVShow
import com.example.popcorn.viewmodel.FavouriteViewModel
import com.example.popcorn.viewmodel.TVShowViewModel

// Adapter used in TVShowList and Home fragments, responsible for displaying list of popular TV Shows and TV Shows with matching title:
class TVShowListAdapter(private val TVShows : LiveData<List<TVShow>>,
                        private val tvShowViewModel : TVShowViewModel,
                        private val favViewModel : FavouriteViewModel,
                        private val inFragment : String) : RecyclerView.Adapter<TVShowListAdapter.TVShowHolder>() {

    inner class TVShowHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowHolder {
        val view: View =
            // Display in rows if in TVShowListFragment (fourth tab):
            if (inFragment == "TVShowListFragment")
                LayoutInflater.from(parent.context).inflate(R.layout.one_item_row, parent, false)
            // Display in tiles if in HomeFragment (first tab):
            else
                LayoutInflater.from(parent.context).inflate(R.layout.one_item_tile, parent, false)
        return TVShowHolder(view)
    }

    override fun onBindViewHolder(holder: TVShowHolder, position: Int) {
        // Binding data in TVShow List Fragment:
        if (inFragment == "TVShowListFragment") {
            // Title:
            val name = holder.itemView.findViewById<TextView>(R.id.tv_titleOrName)
            name.text = TVShows.value?.get(position)?.name.toString()

            // Release date:
            val date = holder.itemView.findViewById<TextView>(R.id.tv_releaseOrBirth)
            if (!TVShows.value?.get(position)?.first_air_date.isNullOrEmpty())
                date.text = TVShows.value?.get(position)?.first_air_date.toString().slice(IntRange(0,3))

            // Poster:
            val poster = holder.itemView.findViewById<ImageView>(R.id.iv_posterOrPhoto)
            val url = "https://image.tmdb.org/t/p/w185${TVShows.value?.get(position)?.poster_path}"
            Glide.with(holder.itemView).load(url).centerCrop().placeholder(R.drawable.ic_twotone_live_tv_24holder).into(poster)

            // Navigation between fragments - going to TV Show Details:
            val movieRowBackground = holder.itemView.findViewById<ConstraintLayout>(R.id.rowBackground)
            movieRowBackground.setOnClickListener {
                TVShows.value?.get(position)?.let { item -> tvShowViewModel.setCurrentTVShow(item.id) }
                movieRowBackground.findNavController().navigate(R.id.action_TVShowListFragment_to_TVShowDetailsFragment)
            }

            // Adding to favourites:
            val addToFav = holder.itemView.findViewById<ImageButton>(R.id.btn_addToFav)
            addToFav.setOnClickListener{ TVShows.value?.get(position)?.let { item -> favViewModel.addFavourite(item) } }

            // Default visibility of "favourite buttons":
            val delFromFav = holder.itemView.findViewById<ImageButton>(R.id.btn_delFromFav)
            val favDate = holder.itemView.findViewById<TextView>(R.id.tv_favDate)
            addToFav.visibility = View.VISIBLE
            delFromFav.visibility = View.GONE
            favDate.text = ""

            // Deleting from favourites:
            val favouriteMovie = favViewModel.favourites.value?.find {
                x -> x.media_type == "tv" && x.movieOrTVShowID == TVShows.value?.get(position)?.id }
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
            name.text = TVShows.value?.get(position)?.name.toString()

            // Who was played:
            val character = holder.itemView.findViewById<TextView>(R.id.tv_characterOrDepartment)
            character.text = TVShows.value?.get(position)?.character

            // Poster:
            val poster = holder.itemView.findViewById<ImageView>(R.id.iv_posterOrPhoto)
            val url = "https://image.tmdb.org/t/p/w185${TVShows.value?.get(position)?.poster_path}"
            Glide.with(holder.itemView).load(url).centerCrop().placeholder(R.drawable.ic_twotone_live_tv_24holder).into(poster)

            // Navigation between fragments - going to TV Show Details:
            val movieRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
            movieRowBackground.setOnClickListener {
                TVShows.value?.let { item -> tvShowViewModel.setCurrentTVShow(item[position].id) }
                movieRowBackground.findNavController().navigate(R.id.action_homeFragment_to_TVShowDetailsFragment)
            }
        }
    }

    override fun getItemCount(): Int = TVShows.value?.size ?: 0
}