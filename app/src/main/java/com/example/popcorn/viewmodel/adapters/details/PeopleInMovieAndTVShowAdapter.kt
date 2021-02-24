package com.example.popcorn.viewmodel.adapters.details

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
import com.example.popcorn.viewmodel.PersonViewModel

// Adapter used in MovieDetails and TVShowDetails fragments, responsible for displaying people that performed in or were in crew of current movie / TV Show:
class PeopleInMovieAndTVShowAdapter(private val people: LiveData<List<Person>>,
                                    private val personViewModel: PersonViewModel,
                                    private val movieOrTVShow : String,
                                    private val castOrCrew : String) : RecyclerView.Adapter<PeopleInMovieAndTVShowAdapter.PersonHolder>() {

    inner class PersonHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_item_tile, parent, false)
        return PersonHolder(view)
    }

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        // Name:
        val name = holder.itemView.findViewById<TextView>(R.id.tv_titleOrName)
        name.text = people.value?.get(position)?.name.toString()

        // Who was played or for what was responsible:
        val character = holder.itemView.findViewById<TextView>(R.id.tv_characterOrDepartment)
        character.text =
                if (castOrCrew == "cast") people.value?.get(position)?.character
                else people.value?.get(position)?.department

        // Photo:
        val avatar = holder.itemView.findViewById<ImageView>(R.id.iv_posterOrPhoto)
        val url = "https://image.tmdb.org/t/p/w185${people.value?.get(position)?.profile_path}"
        val placeholderImg : Int = when (people.value?.get(position)?.gender) {
            2 -> R.drawable.ic_person_placeholder_24
            1 -> R.drawable.ic_person_placeholder_24_2
            else -> R.drawable.ic_person_placeholder_24_e
        }
        Glide.with(holder.itemView).load(url).centerCrop().placeholder(placeholderImg).into(avatar)

        // Navigation between fragments - going to Person Details:
        val rowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
        rowBackground.setOnClickListener {
            people.value?.let { item -> personViewModel.setCurrentPerson(item[position].id) }
            if (movieOrTVShow == "movie")
                rowBackground.findNavController().navigate(R.id.action_movieDetailsFragment_to_personDetailsFragment)
            else
                rowBackground.findNavController().navigate(R.id.action_TVShowDetailsFragment_to_personDetailsFragment)
        }
    }

    override fun getItemCount(): Int = people.value?.size ?: 0
}