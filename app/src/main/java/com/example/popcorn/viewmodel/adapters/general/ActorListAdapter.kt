package com.example.popcorn.viewmodel.adapters.general

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

class ActorListAdapter(val actors: LiveData<List<Person>>, private val personVM : PersonViewModel,private val fromCalled: Int) : RecyclerView.Adapter<ActorListAdapter.ActorHolder>() {
    inner class ActorHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
        val view: View = if(fromCalled == 0) {
            LayoutInflater.from(parent.context).inflate(R.layout.person_row, parent, false)
        }
        else{
            LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        }
        return ActorHolder(view)
    }

    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        if(fromCalled == 0){
        val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
        val avatar = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
        val actorRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.actorRowBackground)
        val url = "https://image.tmdb.org/t/p/w185${actors.value?.get(position)?.profile_path}"
        Glide.with(holder.itemView)
            .load(url)
            .centerCrop()
            .into(avatar)
        name.text = actors.value?.get(position)?.name.toString()
        actorRowBackground.setOnClickListener {  actors.value?.let { it1 -> personVM.setCurrentPerson(it1.get(position).id) }
            actorRowBackground.findNavController().navigate(R.id.action_actorListFragment_to_actorDetailsFragment) }}
        else {
            val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
            val avatar = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
            val character = holder.itemView.findViewById<TextView>(R.id.tv_characterName)
            val actorRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
            val url = "https://image.tmdb.org/t/p/w185${actors.value?.get(position)?.profile_path}"
            Glide.with(holder.itemView)
                    .load(url)
                    .centerCrop()
                    .into(avatar)
            name.text = actors.value?.get(position)?.name.toString()
            character.text = actors.value?.get(position)?.character
            actorRowBackground.setOnClickListener {  actors.value?.let { it1 -> personVM.setCurrentPerson(it1.get(position).id) }
                actorRowBackground.findNavController().navigate(R.id.action_movieDetailsFragment_to_actorDetailsFragment) }
        }
    }

    override fun getItemCount(): Int {
        return actors.value?.size?:0
    }
}