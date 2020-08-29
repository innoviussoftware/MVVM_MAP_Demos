package com.example.mvvm_map_demos

import android.app.Application
import com.example.mvvm_map_demos.data.repository.HomeRepository
import com.example.mvvm_map_demos.network.MyApi
import com.example.mvvm_map_demos.network.NetworkConnectionInterceptor
import com.maeiapp.ui.home.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MyApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {

        import(androidXModule(this@MyApplication))

        //Comman used
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }

        //Login screen Injection
        bind() from singleton { HomeRepository(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }

    }

}