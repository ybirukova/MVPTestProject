package com.example.mvptestproject.di

import android.app.Activity
import com.example.mvptestproject.contract.MainContract
import com.example.mvptestproject.presentation.MainActivity
import com.example.mvptestproject.presentation.MainPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class MainModule {
    @Binds
    abstract fun bindActivity(view: MainActivity): MainContract.View

    @Binds
    abstract fun bindPresenter(impl: MainPresenter): MainContract.Presenter
}

@InstallIn(ActivityComponent::class)
@Module
object MainActivityModule {
    @Provides
    fun bindActivity(activity: Activity): MainActivity {
        return activity as MainActivity
    }
}
