package com.example.popcorn.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.popcorn.R
import com.example.popcorn.viewmodel.MovieViewModel

class HomeFragment : Fragment() {
    private lateinit var movieVM: MovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        movieVM = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        movieVM.setCurrentMovieID(550)
        movieVM.setCurrentMovie()
        movieVM.currentMovie.observe(viewLifecycleOwner, {
            println(movieVM.currentMovie.value.toString())
        })

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object { fun newInstance() = HomeFragment() }
}