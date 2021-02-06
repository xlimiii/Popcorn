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
import com.example.popcorn.viewmodel.PersonViewModel
import com.example.popcorn.viewmodel.adapters.general.PersonListAdapter
import kotlinx.android.synthetic.main.fragment_person_list.*

class PersonListFragment : Fragment() {
    private lateinit var personListAdapter : PersonListAdapter
    private lateinit var myLayoutManager : LinearLayoutManager
    private lateinit var recyclerView : RecyclerView
    private lateinit var personVM : PersonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myLayoutManager = LinearLayoutManager(context)
        personVM = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)

        personVM.setPeopleWithMatchingName("")
        personListAdapter = PersonListAdapter(personVM.peopleWithMatchingName, personVM, "PersonListFragment")

        personVM.peopleWithMatchingName.observe(viewLifecycleOwner, { personListAdapter.notifyDataSetChanged() })

        return inflater.inflate(R.layout.fragment_person_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = rv_actorList.apply {
            this.layoutManager = myLayoutManager
            this.adapter = personListAdapter
        }

        sv_actorList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(givenText : String) : Boolean {
                personVM.setPeopleWithMatchingName(givenText)
                return false
            }

            override fun onQueryTextSubmit(givenText: String): Boolean {
                personVM.setPeopleWithMatchingName(givenText)
                return false
            }
        })
    }
}