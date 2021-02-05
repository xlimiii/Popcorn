package com.example.popcorn.viewmodel.adapters.general

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Placeholder
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        // name:
        val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
        name.text = people.value?.get(position)?.name.toString()

        // photo:
        val avatar = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
        val url = "https://image.tmdb.org/t/p/w185${people.value?.get(position)?.profile_path}"
        val placeholderImg : Int = when(people.value?.get(position)?.gender){
            2 -> R.drawable.ic_baseline_person_outline_24
            1 -> R.drawable.ic_baseline_person_outline_242
            else -> R.drawable.ic_baseline_person_outline_24e
        }
        Glide.with(holder.itemView).load(url).centerCrop().placeholder(placeholderImg).into(avatar)

        if (inFragment == "PersonListFragment")
        {
            // navigation:
            val actorRowBackground = holder.itemView.findViewById<ConstraintLayout>(R.id.actorRowBackground)
            actorRowBackground.setOnClickListener {
                people.value?.let { item -> personVM.setCurrentPerson(item[position].id) }
                actorRowBackground.findNavController().navigate(R.id.action_personListFragment_to_personDetailsFragment)
            }
        }
        else
        {
            // who was played:
            val character = holder.itemView.findViewById<TextView>(R.id.tv_characterName)
            character.text = people.value?.get(position)?.character

            // navigation:
            val actorRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
            actorRowBackground.setOnClickListener {
                people.value?.let { item -> personVM.setCurrentPerson(item[position].id) }
                actorRowBackground.findNavController().navigate(R.id.action_homeFragment_to_personDetailsFragment)
            }
        }
    }

    override fun getItemCount(): Int = people.value?.size ?: 0
}