package com.williamtan.dog.data.di

import com.squareup.moshi.Moshi
import com.williamtan.dog.data.DogApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideDogApi(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): DogApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.thedogapi.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(DogApi::class.java)
}