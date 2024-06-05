package com.example.recyclerviewwithnavigationcomponent.ui.splashScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewwithnavigationcomponent.MyApplication
import com.example.recyclerviewwithnavigationcomponent.databinding.ActivitySplashScreenBinding
import com.example.recyclerviewwithnavigationcomponent.ui.main.MainActivity
import com.example.recyclerviewwithnavigationcomponent.ui.authenctication.AuthenticationActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    private val splashScreenViewModel: SplashScreenViewModel by viewModel<SplashScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler(Looper.getMainLooper()).postDelayed({
            splashScreenViewModel.loadTokenAccount.observe(this){
                Log.d("valueAuth", "valueAuth: $it")
                if (it.isNullOrEmpty()){
                    startActivity(Intent(this,AuthenticationActivity::class.java))
                    finish()
                }else{
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        },2_000)
    }
}