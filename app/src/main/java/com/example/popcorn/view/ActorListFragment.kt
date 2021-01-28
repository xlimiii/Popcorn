package com.example.popcorn.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.viewmodel.PersonViewModel
import com.example.popcorn.viewmodel.adapters.ActorListAdapter
import kotlinx.android.synthetic.main.fragment_actor_list.*

class ActorListFragment : Fragment() {
    private lateinit var actorListAdapter: ActorListAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myLayoutManager= LinearLayoutManager(context)

        viewModel= ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)

        actorListAdapter= ActorListAdapter(viewModel.popularPeople)

        viewModel.popularPeople.observe(viewLifecycleOwner, Observer { t ->
            actorListAdapter.notifyDataSetChanged()
        })
        return inflater.inflate(R.layout.fragment_actor_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=rv_actorList.apply {
            this.layoutManager=myLayoutManager
            this.adapter=actorListAdapter
        }
    }

    companion object { fun newInstance() = ActorListFragment() }
}