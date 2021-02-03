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

class PeopleInMovieAndTVShowAdapter(private val people: LiveData<List<Person>>,
                                    private val personVM: PersonViewModel,
                                    private val movieOrTVShow : String,
                                    private val castOrCrew : String) : RecyclerView.Adapter<PeopleInMovieAndTVShowAdapter.PersonHolder>() {

    inner class PersonHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        return PersonHolder(view)
    }

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        // name:
        val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
        name.text = people.value?.get(position)?.name.toString()

        // who was played:
        val character = holder.itemView.findViewById<TextView>(R.id.tv_characterName)
        character.text =
                if (castOrCrew == "cast") people.value?.get(position)?.character
                else people.value?.get(position)?.department

        // photo:
        val avatar = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
        if (!people.value?.get(position)?.profile_path.isNullOrEmpty())
        {
            val url = "https://image.tmdb.org/t/p/w185${people.value?.get(position)?.profile_path}"
            Glide.with(holder.itemView).load(url).centerCrop().into(avatar)
        }
        //else Glide.with(holder.itemView).load("LINK HERE").centerCrop().into(avatar)

        // navigation:
        val rowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
        rowBackground.setOnClickListener {
            people.value?.let { item -> personVM.setCurrentPerson(item[position].id) }
            if (movieOrTVShow == "movie")
                rowBackground.findNavController().navigate(R.id.action_movieDetailsFragment_to_personDetailsFragment)
            else
                rowBackground.findNavController().navigate(R.id.action_TVShowDetailsFragment_to_personDetailsFragment)
        }
    }

    override fun getItemCount(): Int = people.value?.size ?: 0
}