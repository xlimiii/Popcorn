package com.example.popcorn.viewmodel.adapters.general

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popcorn.R
import com.example.popcorn.model.GeneralObject
import com.example.popcorn.viewmodel.MultiViewModel

class MultiListAdapter(val objects: LiveData<List<GeneralObject>>, private val multiVM : MultiViewModel) : RecyclerView.Adapter<MultiListAdapter.ObjectHolder>() {
    inner class ObjectHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_row, parent, false)
        return ObjectHolder(view)
    }

    override fun onBindViewHolder(holder: ObjectHolder, position: Int) {
        val name = holder.itemView.findViewById<TextView>(R.id.tv_personName)
        val avatar = holder.itemView.findViewById<ImageView>(R.id.iv_personAvatar)
        val objectRowBackground = holder.itemView.findViewById<LinearLayout>(R.id.actorRowBackground)

        if(objects.value?.get(position)?.media_type == "movie") {
            val url = "https://image.tmdb.org/t/p/w185${objects.value?.get(position)?.poster_path}"
            Glide.with(holder.itemView)
                    .load(url)
                    .centerCrop()
                    .into(avatar)
            name.text = objects.value?.get(position)?.title.toString()
            objectRowBackground.setOnClickListener {

            }
        }
        else if(objects.value?.get(position)?.media_type == "tv") {
            val url = "https://image.tmdb.org/t/p/w185${objects.value?.get(position)?.poster_path}"
            Glide.with(holder.itemView)
                    .load(url)
                    .centerCrop()
                    .into(avatar)
            name.text = objects.value?.get(position)?.name.toString()
            objectRowBackground.setOnClickListener {

            }
        }
        else if(objects.value?.get(position)?.media_type == "person"){
            val url = "https://image.tmdb.org/t/p/w185${objects.value?.get(position)?.profile_path}"
            Glide.with(holder.itemView)
                    .load(url)
                    .centerCrop()
                    .into(avatar)
            name.text = objects.value?.get(position)?.name.toString()
            objectRowBackground.setOnClickListener {

            }
        }
    }

    override fun getItemCount(): Int {
        return objects.value?.size?:0
    }
}