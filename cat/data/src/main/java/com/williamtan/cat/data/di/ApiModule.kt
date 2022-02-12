package com.williamtan.cat.data.di

import com.williamtan.cat.data.CatApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    internal fun provideRetrofit() = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/v1/")
        .build()

    @Provides
    internal fun provideCatApi(retrofit: Retrofit) = retrofit.create(CatApi::class.java)
}