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

class PersonListAdapter(private val people: LiveData<List<Person>>,
                        private val personVM : PersonViewModel,
                        private val inFragment : String) : RecyclerView.Adapter<PersonListAdapter.PersonHolder>() {

    inner class PersonHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        val view : View =
                if (inFragment == "PersonListFragment")
                    LayoutInflater.from(parent.context).inflate(R.layout.person_row, parent, false)
                else
                    LayoutInflater.from(parent.context).inflate(R.layout.tile, parent, false)
        return PersonHolder(view)
    }

    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
        name.text = people.value?.get(position)?.name.toString()

        val avatar = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
        val url = "https://image.tmdb.org/t/p/w185${people.value?.get(position)?.profile_path}"
        Glide.with(holder.itemView).load(url).centerCrop().into(avatar)

        if (inFragment == "PersonListFragment") {
            val actorRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.actorRowBackground)
            actorRowBackground.setOnClickListener {
                people.value?.let { item -> personVM.setCurrentPerson(item[position].id) }
                actorRowBackground.findNavController().navigate(R.id.action_personListFragment_to_personDetailsFragment)
            }
        } else {
            val character = holder.itemView.findViewById<TextView>(R.id.tv_characterName)
            character.text = people.value?.get(position)?.character

            val actorRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
            actorRowBackground.setOnClickListener {
                people.value?.let { item -> personVM.setCurrentPerson(item[position].id) }
                actorRowBackground.findNavController().navigate(R.id.action_homeFragment_to_personDetailsFragment)
            }
        }
    }

    override fun getItemCount(): Int = people.value?.size ?: 0
}