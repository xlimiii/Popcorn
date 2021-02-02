package com.example.popcorn.view.general

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.viewmodel.FavouriteViewModel
import com.example.popcorn.viewmodel.TVShowViewModel
import com.example.popcorn.viewmodel.adapters.general.TVShowListAdapter
import kotlinx.android.synthetic.main.fragment_tv_show_list.*

class TvShowListFragment : Fragment() {
    private lateinit var TVShowListAdapter : TVShowListAdapter
    private lateinit var myLayoutManager : LinearLayoutManager
    private lateinit var recyclerView : RecyclerView
    private lateinit var TVShowVM : TVShowViewModel
    private lateinit var favVM : FavouriteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myLayoutManager = LinearLayoutManager(context)
        TVShowVM = ViewModelProvider(requireActivity()).get(TVShowViewModel::class.java)
        favVM = ViewModelProvider(requireActivity()).get(FavouriteViewModel::class.java)

        TVShowVM.setTVShowsWithMatchingTitle("")
        TVShowListAdapter = TVShowListAdapter(TVShowVM.TVShowsWithMatchingTitle, TVShowVM, favVM, 0)
        TVShowVM.TVShowsWithMatchingTitle.observe(viewLifecycleOwner, { TVShowListAdapter.notifyDataSetChanged() })
        favVM.favourites.observe(viewLifecycleOwner, { TVShowListAdapter.notifyDataSetChanged() })

        return inflater.inflate(R.layout.fragment_tv_show_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = rv_tvShowList.apply {
            this.layoutManager = myLayoutManager
            this.adapter = TVShowListAdapter
        }

        sv_tvShowList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(givenText : String) : Boolean { TVShowVM.setTVShowsWithMatchingTitle(givenText); return false }
            override fun onQueryTextSubmit(query: String): Boolean { return false }
        })
    }

    companion object { fun newInstance() = TvShowListFragment() }
}