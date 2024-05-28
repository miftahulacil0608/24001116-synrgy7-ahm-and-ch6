package com.example.recyclerviewwithnavigationcomponent.ui.authenctication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.recyclerviewwithnavigationcomponent.R
import com.example.recyclerviewwithnavigationcomponent.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityAuthenticationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigationComponentWithAppbar()

       /* viewModel.loading.observe(this) { loading ->
            if (loading) binding.btnFlipper.displayedChild = 0
            else binding.btnFlipper.displayedChild = 1
        }*/

    }
    private fun setupNavigationComponentWithAppbar(){
        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_authentication) as NavHostFragment
        setupActionBarWithNavController(host.navController)
    }
}