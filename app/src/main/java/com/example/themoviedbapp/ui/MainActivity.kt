package com.example.themoviedbapp.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.themoviedbapp.MainApp
import com.example.themoviedbapp.R

/**
 * @author AliAzazAlam on 5/16/2021.
 */
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        * Setup action bar
        * */
        setupNavigation()

        /*
        * Screen orientation setting
        * */
        requestedOrientation = if (MainApp.layoutFlag) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        }
    }

    fun setupNavigation() {
        // Navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}