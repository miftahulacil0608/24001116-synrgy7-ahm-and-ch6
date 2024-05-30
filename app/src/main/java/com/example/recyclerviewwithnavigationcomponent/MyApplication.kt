package com.example.recyclerviewwithnavigationcomponent

import android.app.Application
import com.example.recyclerviewwithnavigationcomponent.di.Module
import com.example.recyclerviewwithnavigationcomponent.viewmodelfactory.ViewModelFactory

class MyApplication: Application() {

    lateinit var module: Module
    lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate() {
        super.onCreate()
        module = Module(this)
        viewModelFactory = ViewModelFactory(this.module)

    }
}