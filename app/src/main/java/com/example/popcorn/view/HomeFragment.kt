package com.example.popcorn.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.popcorn.R
import com.example.popcorn.viewmodel.ApiViewModel

class HomeFragment : Fragment() {
    private lateinit var apiVM: ApiViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        apiVM = ViewModelProvider(requireActivity()).get(ApiViewModel::class.java)
        apiVM.setCurrentMovieID(550)
        apiVM.setCurrentMovie()
        apiVM.currentMovie.observe(viewLifecycleOwner, {
            println(apiVM.currentMovie.value.toString())
        })

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object { fun newInstance() = HomeFragment() }
}