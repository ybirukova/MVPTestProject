package com.example.mvptestproject.di

import com.example.mvptestproject.data.network.WeatherForecastService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val baseUrl = "https://api.openweathermap.org/data/2.5/"
    }

    @Provides
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.HEADERS
                },
            )
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun getService(retrofit: Retrofit): WeatherForecastService {
        return retrofit.create(WeatherForecastService::class.java)
    }
}
