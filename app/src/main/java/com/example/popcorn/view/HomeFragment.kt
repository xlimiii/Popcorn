package com.example.popcorn.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.popcorn.R
import com.example.popcorn.viewmodel.CompanyViewModel
import com.example.popcorn.viewmodel.MovieViewModel
import com.example.popcorn.viewmodel.PersonViewModel

class HomeFragment : Fragment() {
    //private lateinit var movieVM : MovieViewModel
    //private lateinit var personVM : PersonViewModel
    //private lateinit var companyVM : CompanyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        /*

        movieVM = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        personVM = ViewModelProvider(requireActivity()).get(PersonViewModel::class.java)
        companyVM = ViewModelProvider(requireActivity()).get(CompanyViewModel::class.java)

        movieVM.setMoviesWithMatchingTitle("Avengers")
        movieVM.moviesWithMatchingTitle.observe(viewLifecycleOwner, {
            println("Avenger movies: " + movieVM.moviesWithMatchingTitle.value.toString()) })

        movieVM.setPopularMovies()
        movieVM.popularMovies.observe(viewLifecycleOwner, {
            println("Popular movies: " + movieVM.popularMovies.value.toString()) })

        movieVM.setCurrentMovie(550)
        movieVM.currentMovie.observe(viewLifecycleOwner, {
            println("Movie number 550: " + movieVM.currentMovie.value.toString()) })

        movieVM.setPeopleConnectedWithCurrentMovie(550)
        movieVM.peopleConnectedWithCurrentMovie.observe(viewLifecycleOwner, {
            println("People connected with movie number 550: " + movieVM.peopleConnectedWithCurrentMovie.value.toString()) })

        movieVM.setGenres()
        movieVM.genres.observe(viewLifecycleOwner, {
            println("Genres: " + movieVM.genres.value.toString()) })

        personVM.setPeopleWithMatchingName("Gal")
        personVM.peopleWithMatchingName.observe(viewLifecycleOwner, {
            println("'Gal' people: " + personVM.peopleWithMatchingName.value.toString()) })

        personVM.setPopularPeople()
        personVM.popularPeople.observe(viewLifecycleOwner, {
            println("Popular people: " + personVM.popularPeople.value.toString()) })

        personVM.setCurrentPerson(50)
        personVM.currentPerson.observe(viewLifecycleOwner, {
            println("Person number 50: " + personVM.currentPerson.value.toString()) })

        personVM.setMoviesConnectedWithCurrentPerson(50)
        personVM.moviesConnectedWithCurrentPerson.observe(viewLifecycleOwner, {
            println("Movies connected with person number 50: " + personVM.moviesConnectedWithCurrentPerson.value.toString()) })

        companyVM.setCompaniesWithMatchingName("Disney")
        companyVM.companiesWithMatchingName.observe(viewLifecycleOwner, {
            println("Disney companies: " + companyVM.companiesWithMatchingName.value.toString()) })

        companyVM.setCurrentCompany(3)
        companyVM.currentCompany.observe(viewLifecycleOwner, {
            println("Company number 3: " + companyVM.currentCompany.value.toString()) })

         */

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object { fun newInstance() = HomeFragment() }
}