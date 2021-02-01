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

class TVShowDetailsAdapter(private val peopleInShow: LiveData<List<Person>>,
                           private val personVM: PersonViewModel) : RecyclerView.Adapter<TVShowDetailsAdapter.TVShowHolder>() {
    inner class TVShowHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        return TVShowHolder(view)
    }

    override fun onBindViewHolder(holder: TVShowHolder, position: Int) {

        val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
        val avatar = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
        val character = holder.itemView.findViewById<TextView>(R.id.tv_characterName)
        val actorRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
        val url = "https://image.tmdb.org/t/p/w185${peopleInShow.value?.get(position)?.profile_path}"
        Glide.with(holder.itemView).load(url).centerCrop().into(avatar)
        name.text = peopleInShow.value?.get(position)?.name.toString()
        character.text = peopleInShow.value?.get(position)?.character
        actorRowBackground.setOnClickListener {  peopleInShow.value?.let { it1 -> personVM.setCurrentPerson(it1.get(position).id) }
            actorRowBackground.findNavController().navigate(R.id.action_TVShowDetailsFragment_to_actorDetailsFragment) }
    }

    override fun getItemCount() : Int = peopleInShow.value?.size ?: 0
}