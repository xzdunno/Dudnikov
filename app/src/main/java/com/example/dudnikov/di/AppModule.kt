package com.example.dudnikov.di

import com.example.dudnikov.data.network.RetroInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    val BASE_URL = "https://kinopoiskapiunofficial.tech/"

@Provides
@Singleton
fun getRetroInterface(retrofit: Retrofit): RetroInterface {
    return retrofit.create(RetroInterface::class.java)
}

@Provides
@Singleton
fun getNetworkInstance(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
}