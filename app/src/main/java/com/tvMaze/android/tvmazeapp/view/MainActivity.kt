package com.tvMaze.android.tvmazeapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.tvMaze.android.tvmazeapp.R
import com.tvMaze.android.tvmazeapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(mainBinding.root)

        navController = Navigation.findNavController(this, R.id.fragmentContainerView)


        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        NavigationUI.navigateUp(navController, null)
}