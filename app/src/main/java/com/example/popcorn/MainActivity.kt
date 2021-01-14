package com.example.popcorn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

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
}