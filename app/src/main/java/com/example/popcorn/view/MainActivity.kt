package com.example.popcorn.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.popcorn.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment.newInstance()
    private val movieListFragment = MovieListFragment.newInstance()
    private val actorListFragment = ActorListFragment.newInstance()
    private val directorListFragment = DirectorListFragment.newInstance()
    private val favouriteListFragment = FavouriteListFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menu_mainMenu -> replaceFragment(movieListFragment)
                R.id.menu_actors -> replaceFragment(actorListFragment)
                R.id.menu_movies -> replaceFragment(movieListFragment)
                R.id.menu_directors -> replaceFragment(directorListFragment)
                R.id.menu_favourites -> replaceFragment(favouriteListFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}