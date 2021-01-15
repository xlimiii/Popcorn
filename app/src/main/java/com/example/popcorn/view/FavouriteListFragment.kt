package com.example.popcorn.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.popcorn.R
import com.example.popcorn.viewmodel.FavouriteViewModel

class FavouriteListFragment : Fragment() {
    private lateinit var favVM: FavouriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        favVM = ViewModelProvider(requireActivity()).get(FavouriteViewModel::class.java)
        favVM.addFavourite(550)
        favVM.favourites.observe(viewLifecycleOwner, {
            println(favVM.favourites.value.toString())
            if (!favVM.favourites.value.isNullOrEmpty()) favVM.deleteFavorite(550)
        })

        return inflater.inflate(R.layout.fragment_favourite_list, container, false)
    }

    companion object { fun newInstance() = FavouriteListFragment() }
}