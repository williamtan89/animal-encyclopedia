package com.williamtan.cat.data.di

import com.squareup.moshi.Moshi
import com.williamtan.cat.data.CatApi
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
    fun provideCatApi(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): CatApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.thecatapi.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(CatApi::class.java)
}