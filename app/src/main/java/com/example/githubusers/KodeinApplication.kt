package com.example.githubusers

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.githubusers.data.network.ApiInterface
import com.example.githubusers.data.repositories.UserRepository
import com.example.githubusers.features.list.viewmodel.MainActivityViewModelFactory
import com.example.githubusers.features.user.viewmodel.UserDetailsViewModelFactory
import org.kodein.di.generic.bind
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/*
* Kodein DI framework main class,
* where the required classes are injected by singleton
* */
class KodeinApplication : Application(), KodeinAware {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override val kodein = Kodein.lazy {
        import(androidXModule(this@KodeinApplication))

        bind() from  singleton { ApiInterface() }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { UserDetailsViewModelFactory(instance(), instance()) }
        bind() from singleton { MainActivityViewModelFactory(instance(), instance()) }

    }

}