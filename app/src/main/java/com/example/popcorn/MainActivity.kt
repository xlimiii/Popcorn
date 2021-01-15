package com.example.popcorn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.popcorn.Model.ApiRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
const val   BASE_URL="https://api.themoviedb.org"
class MainActivity : AppCompatActivity() {

    private val homeFragment= HomeFragment()
    private val movieListFragment= MovieListFragment()
    private val actorListFragment= ActorListFragment()
    private val directorListFragment= DirectorListFragment()
    private val favourtieListFragment= FavouriteListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)
        getMovieInfo()


        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_mainMenu -> replaceFragment(movieListFragment)
                R.id.menu_actors -> replaceFragment(actorListFragment)
                R.id.menu_movies -> replaceFragment(movieListFragment)
                R.id.menu_directors -> replaceFragment(directorListFragment)
                R.id.menu_favourites -> replaceFragment(favourtieListFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    private fun getMovieInfo(){

        val api= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)
        
        GlobalScope.launch(Dispatchers.IO) {
            val response=api.getMovieDetails().awaitResponse()

            if(response.isSuccessful)
            {
                val data=response.body()!!
                Log.d("rr", "${data?.original_title?: "brak nazwy miasta"}")
            }

        }

    }

}