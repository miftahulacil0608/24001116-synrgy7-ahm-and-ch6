package com.example.recyclerviewwithnavigationcomponent

import android.app.Application
import com.example.recyclerviewwithnavigationcomponent.di.Module
import com.example.recyclerviewwithnavigationcomponent.di.koinModule
import com.example.recyclerviewwithnavigationcomponent.viewmodelfactory.ViewModelFactory
import com.example.recyclerviewwithnavigationcomponent.viewmodelfactory.koinViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication: Application() {

    //Dependency Injection Manual
    private lateinit var module: Module
    private lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate() {
        super.onCreate()
        //Dependency Injection Manual
        module = Module(this)
        viewModelFactory = ViewModelFactory(this.module)

//Dependency Injection Koin
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(koinModule, koinViewModelModule)
        }
    }
}