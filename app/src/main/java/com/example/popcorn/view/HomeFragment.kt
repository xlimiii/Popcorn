package com.example.popcorn.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.viewmodel.*
import com.example.popcorn.viewmodel.adapters.ActorListAdapter
import com.example.popcorn.viewmodel.adapters.MovieListAdapter
import com.example.popcorn.viewmodel.adapters.MultiListAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.fragment_movie_list.rv_movieList
import kotlinx.android.synthetic.main.fragment_movie_list.sv_movieList

class HomeFragment : Fragment() {
    private lateinit var multiListAdapter : MultiListAdapter
    private lateinit var myLayoutManager : LinearLayoutManager
    private lateinit var recyclerView : RecyclerView
    private lateinit var multiVM : MultiViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myLayoutManager = LinearLayoutManager(context)
        multiVM = ViewModelProvider(requireActivity()).get(MultiViewModel::class.java)

        multiVM.setObjectsWithMatchingName("")
        multiListAdapter = MultiListAdapter(multiVM.objectsWithMatchingName, multiVM)
        multiVM.objectsWithMatchingName.observe(viewLifecycleOwner, { multiListAdapter.notifyDataSetChanged() })


        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = rv_multiList.apply {
            this.layoutManager = myLayoutManager
            this.adapter = multiListAdapter
        }

        sv_multiList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(givenText : String) : Boolean { multiVM.setObjectsWithMatchingName(givenText); return false }
            override fun onQueryTextSubmit(query: String): Boolean { return false }
        })
    }

    companion object { fun newInstance() = HomeFragment() }
}