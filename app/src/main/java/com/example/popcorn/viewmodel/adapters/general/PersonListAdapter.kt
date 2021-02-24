package com.example.popcorn.viewmodel.adapters.general

import android.annotation.SuppressLint
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
import com.example.popcorn.model.Person
import com.example.popcorn.viewmodel.PersonViewModel

// Adapter used in PersonList and Home fragments, responsible for displaying list of popular people and people with matching title:
class PersonListAdapter(private val people: LiveData<List<Person>>,
                        private val personViewModel : PersonViewModel,
                        private val inFragment : String) : RecyclerView.Adapter<PersonListAdapter.PersonHolder>() {

    inner class PersonHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonHolder {
        val view : View =
            // Display in rows if in PersonListFragment (third tab):
            if (inFragment == "PersonListFragment")
                LayoutInflater.from(parent.context).inflate(R.layout.one_item_row, parent, false)
            // Display in tiles if in HomeFragment (first tab):
            else
                LayoutInflater.from(parent.context).inflate(R.layout.one_item_tile, parent, false)
        return PersonHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PersonHolder, position: Int) {
        // Name:
        val name = holder.itemView.findViewById<TextView>(R.id.tv_titleOrName)
        name.text = people.value?.get(position)?.name.toString()

        // Photo:
        val avatar = holder.itemView.findViewById<ImageView>(R.id.iv_posterOrPhoto)
        val url = "https://image.tmdb.org/t/p/w185${people.value?.get(position)?.profile_path}"
        val placeholderImg : Int = when(people.value?.get(position)?.gender) {
            2 -> R.drawable.ic_person_placeholder_24        // man
            1 -> R.drawable.ic_person_placeholder_24_2      // woman
            else -> R.drawable.ic_person_placeholder_24_e   // unknown
        }
        Glide.with(holder.itemView).load(url).centerCrop().placeholder(placeholderImg).into(avatar)

        // Binding data in Person List Fragment:
        if (inFragment == "PersonListFragment")
        {
            // Hide "favourite buttons":
            holder.itemView.findViewById<ImageButton>(R.id.btn_addToFav).visibility = View.GONE
            holder.itemView.findViewById<ImageButton>(R.id.btn_delFromFav).visibility = View.GONE
            holder.itemView.findViewById<TextView>(R.id.tv_favDate).visibility = View.GONE

            // Navigation between fragments - going to Person Details:
            val actorRowBackground = holder.itemView.findViewById<ConstraintLayout>(R.id.rowBackground)
            actorRowBackground.setOnClickListener {
                people.value?.let { item -> personViewModel.setCurrentPerson(item[position].id) }
                actorRowBackground.findNavController().navigate(R.id.action_personListFragment_to_personDetailsFragment)
            }
        }
        else
        {
            // Who was played:
            val character = holder.itemView.findViewById<TextView>(R.id.tv_characterOrDepartment)
            character.text = people.value?.get(position)?.character

            // Navigation between fragments - going to Person Details:
            val actorRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.tileBackground)
            actorRowBackground.setOnClickListener {
                people.value?.let { item -> personViewModel.setCurrentPerson(item[position].id) }
                actorRowBackground.findNavController().navigate(R.id.action_homeFragment_to_personDetailsFragment)
            }
        }
    }

    override fun getItemCount(): Int = people.value?.size ?: 0
}