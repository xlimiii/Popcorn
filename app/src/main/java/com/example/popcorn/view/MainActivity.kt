package com.example.popcorn.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.popcorn.R
import com.google.android.material.bottomnavigation.BottomNavigationView

// Main window:
class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavigation()
    }

    // Setting up navigation that can be seen at the bottom of the screen - it consists of 5 buttons:
    private fun setUpNavigation() {
        bottomNavigationView = findViewById(R.id.bttm_nav)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment!!.navController)
    }
}