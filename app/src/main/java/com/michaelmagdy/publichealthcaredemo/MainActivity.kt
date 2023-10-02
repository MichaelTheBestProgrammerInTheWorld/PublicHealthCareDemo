package com.michaelmagdy.publicHealthCareDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.michaelmagdy.publicHealthCareDemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.name
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment_activity_bottem_navigation)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //  navController.navigate(R.id.navigation_homfragemnt)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.home_fragment,
            R.id.createAccountFragment, R.id.signIn_fragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

    }
}