package com.example.recyclerviewwithnavigationcomponent.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.recyclerviewwithnavigationcomponent.R
import com.example.recyclerviewwithnavigationcomponent.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

//View
class MainActivity : AppCompatActivity() {

    //private val viewModelMain:SharedViewModel by viewModel<SharedViewModel>()

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigationComponentWithAppbar()
    }
    private fun setupNavigationComponentWithAppbar(){
        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        setupActionBarWithNavController(host.navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        return host.navController.navigateUp() || super.onSupportNavigateUp()
    }
}