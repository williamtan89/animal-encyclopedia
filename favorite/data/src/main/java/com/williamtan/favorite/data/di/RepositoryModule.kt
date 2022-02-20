package com.williamtan.favorite.data.di

import com.williamtan.animalencyclopedia.favorite.domain.repository.FavoriteRepository
import com.williamtan.favorite.data.repository.FavoriteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideFavoriteRepository(
    ): FavoriteRepository = FavoriteRepositoryImpl()
}