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
import kotlinx.android.synthetic.main.fragment_general_list.*

// Fragment displayed in third tab:
class PersonListFragment : Fragment() {
    // ViewModel:
    private lateinit var personVM : PersonViewModel

    // Adapter and its RecyclerView:
    private lateinit var personListAdapter : PersonListAdapter
    private lateinit var myLayoutManager : LinearLayoutManager
    private lateinit var recyclerView : RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // ViewModel:
        personVM = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)

        // LinearLayoutManager (used by RecyclerView):
        myLayoutManager = LinearLayoutManager(context)

        // Initializing list of popular people (by empty input):
        personVM.setPeopleWithMatchingName("")

        // Adapter (used by RecyclerView):
        personListAdapter = PersonListAdapter(personVM.peopleWithMatchingName, personVM, "PersonListFragment")

        // Updating people's RecyclerView after receiving response from API:
        personVM.peopleWithMatchingName.observe(viewLifecycleOwner, { personListAdapter.notifyDataSetChanged() })

        return inflater.inflate(R.layout.fragment_general_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView which displays popular people or people with matching name:
        recyclerView = rv_generalList.apply {
            this.layoutManager = myLayoutManager
            this.adapter = personListAdapter
        }

        // Search view responsible for finding people with matching name:
        sv_generalList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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