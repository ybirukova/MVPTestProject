package com.example.mvptestproject.di

import com.example.mvptestproject.data.repositories.WeatherForecastRepositoryImpl
import com.example.mvptestproject.domain.repository.WeatherForecastRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRecipeRepository(impl: WeatherForecastRepositoryImpl): WeatherForecastRepository
}
